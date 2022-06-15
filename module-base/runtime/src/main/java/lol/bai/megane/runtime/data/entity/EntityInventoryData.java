package lol.bai.megane.runtime.data.entity;

import java.util.List;

import lol.bai.megane.api.provider.ItemProvider;
import lol.bai.megane.runtime.registry.Registrar;
import lol.bai.megane.runtime.util.Keys;
import lol.bai.megane.runtime.util.MeganeUtils;
import mcp.mobius.waila.api.IServerAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

import static net.minecraft.util.registry.Registry.ITEM;

public class EntityInventoryData extends EntityData {

    public EntityInventoryData() {
        super(Registrar.INVENTORY, () -> MeganeUtils.config().entityInventory);
    }

    @Override
    @SuppressWarnings("rawtypes")
    void append(NbtCompound data, IServerAccessor<Entity> accessor) {
        List<ItemProvider> providers = Registrar.INVENTORY.get(accessor.getTarget());
        for (ItemProvider provider : providers) {
            setContext(provider, accessor);
            if (provider.hasItems()) {
                data.putBoolean(Keys.I_HAS, true);
                data.putBoolean(Keys.I_SHOW, MeganeUtils.config().entityInventory.isItemCount());
                int size = provider.getSlotCount();
                int i = 0;
                for (int j = 0; j < size; j++) {
                    ItemStack stack = provider.getStack(j);
                    if (stack.isEmpty()) {
                        continue;
                    }
                    data.putInt(Keys.I_ID + i, ITEM.getRawId(stack.getItem()));
                    data.putInt(Keys.I_COUNT + i, stack.getCount());
                    NbtCompound nbt = stack.getNbt();
                    data.put(Keys.I_NBT + i, nbt == null || !MeganeUtils.config().entityInventory.isNbt() ? MeganeUtils.EMPTY_TAG : nbt);
                    i++;
                }
                data.putInt(Keys.I_SIZE, i);
                return;
            }
            if (provider.blockOtherProvider()) {
                return;
            }
        }
    }

}
