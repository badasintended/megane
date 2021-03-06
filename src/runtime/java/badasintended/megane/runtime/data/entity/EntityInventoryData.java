package badasintended.megane.runtime.data.entity;

import java.util.List;

import badasintended.megane.api.provider.InventoryProvider;
import badasintended.megane.runtime.registry.Registrar;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import static badasintended.megane.runtime.util.Keys.I_COUNT;
import static badasintended.megane.runtime.util.Keys.I_HAS;
import static badasintended.megane.runtime.util.Keys.I_ID;
import static badasintended.megane.runtime.util.Keys.I_NBT;
import static badasintended.megane.runtime.util.Keys.I_SHOW;
import static badasintended.megane.runtime.util.Keys.I_SIZE;
import static badasintended.megane.runtime.util.RuntimeUtils.EMPTY_TAG;
import static badasintended.megane.util.MeganeUtils.config;
import static net.minecraft.util.registry.Registry.ITEM;

public class EntityInventoryData extends EntityData {

    public EntityInventoryData() {
        super(Registrar.INVENTORY, () -> config().entityInventory);
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    void append(NbtCompound data, ServerPlayerEntity player, World world, Entity entity) {
        List<InventoryProvider> providers = Registrar.INVENTORY.get(entity);
        for (InventoryProvider provider : providers) {
            provider.setupContext(world, player);
            if (provider.hasInventory(entity)) {
                data.putBoolean(I_HAS, true);
                data.putBoolean(I_SHOW, config().entityInventory.isItemCount());
                int size = provider.size(entity);
                int i = 0;
                for (int j = 0; j < size; j++) {
                    ItemStack stack = provider.getStack(entity, j);
                    if (stack.isEmpty()) {
                        continue;
                    }
                    data.putInt(I_ID + i, ITEM.getRawId(stack.getItem()));
                    data.putInt(I_COUNT + i, stack.getCount());
                    NbtCompound nbt = stack.getTag();
                    data.put(I_NBT + i, nbt == null || !config().entityInventory.isNbt() ? EMPTY_TAG : nbt);
                    i++;
                }
                data.putInt(I_SIZE, i);
                return;
            }
        }
    }

}
