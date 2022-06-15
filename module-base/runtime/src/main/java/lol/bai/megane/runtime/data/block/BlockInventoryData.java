package lol.bai.megane.runtime.data.block;

import java.util.List;

import lol.bai.megane.api.provider.ItemProvider;
import lol.bai.megane.runtime.registry.Registrar;
import lol.bai.megane.runtime.util.MeganeUtils;
import mcp.mobius.waila.api.IServerAccessor;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

import static lol.bai.megane.runtime.util.Keys.I_COUNT;
import static lol.bai.megane.runtime.util.Keys.I_HAS;
import static lol.bai.megane.runtime.util.Keys.I_ID;
import static lol.bai.megane.runtime.util.Keys.I_NBT;
import static lol.bai.megane.runtime.util.Keys.I_SHOW;
import static lol.bai.megane.runtime.util.Keys.I_SIZE;
import static lol.bai.megane.runtime.util.MeganeUtils.EMPTY_TAG;
import static net.minecraft.util.registry.Registry.ITEM;

public class BlockInventoryData extends BlockData {

    public BlockInventoryData() {
        super(Registrar.INVENTORY, () -> MeganeUtils.config().inventory);
    }

    @Override
    @SuppressWarnings("rawtypes")
    void append(NbtCompound data, IServerAccessor<BlockEntity> accessor) {
        data.putBoolean(I_SHOW, MeganeUtils.config().inventory.isItemCount());
        List<ItemProvider> providers = Registrar.INVENTORY.get(accessor.getTarget());
        for (ItemProvider provider : providers) {
            setContext(provider, accessor);
            if (provider.hasItems()) {
                data.putBoolean(I_HAS, true);
                int size = provider.getSlotCount();
                int i = 0;
                for (int j = 0; j < size; j++) {
                    ItemStack stack = provider.getStack(j);
                    if (stack.isEmpty()) {
                        continue;
                    }
                    data.putInt(I_ID + i, ITEM.getRawId(stack.getItem()));
                    data.putInt(I_COUNT + i, stack.getCount());
                    NbtCompound nbt = stack.getNbt();
                    data.put(I_NBT + i, nbt == null || !MeganeUtils.config().inventory.isNbt() ? EMPTY_TAG : nbt);
                    i++;
                }
                data.putInt(I_SIZE, i);
                return;
            }
            if (provider.blockOtherProvider()) {
                return;
            }
        }
    }

}
