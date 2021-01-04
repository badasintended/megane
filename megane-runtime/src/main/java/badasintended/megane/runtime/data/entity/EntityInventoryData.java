package badasintended.megane.runtime.data.entity;

import badasintended.megane.api.provider.InventoryProvider;
import badasintended.megane.runtime.data.Appender;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import static badasintended.megane.api.registry.TooltipRegistry.ENTITY_INVENTORY;
import static badasintended.megane.runtime.util.Keys.I_COUNT;
import static badasintended.megane.runtime.util.Keys.I_HAS;
import static badasintended.megane.runtime.util.Keys.I_ID;
import static badasintended.megane.runtime.util.Keys.I_NBT;
import static badasintended.megane.runtime.util.Keys.I_SHOW;
import static badasintended.megane.runtime.util.Keys.I_SIZE;
import static badasintended.megane.runtime.util.RuntimeUtils.EMPTY_TAG;
import static badasintended.megane.runtime.util.RuntimeUtils.errorData;
import static badasintended.megane.util.MeganeUtils.config;
import static net.minecraft.util.registry.Registry.ITEM;

public class EntityInventoryData extends EntityData {

    public EntityInventoryData() {
        super(() -> config().entityInventory);
        appenders.add(new Registered());
    }

    public static class Registered implements Appender<LivingEntity> {

        @Override
        @SuppressWarnings({"rawtypes", "unchecked"})
        public boolean append(CompoundTag data, ServerPlayerEntity player, World world, LivingEntity livingEntity) {
            try {
                InventoryProvider provider = ENTITY_INVENTORY.get(livingEntity);
                if (provider == null || !provider.hasInventory(livingEntity))
                    return false;

                data.putBoolean(I_HAS, true);
                data.putBoolean(I_SHOW, config().entityInventory.isItemCount());
                int size = provider.size(livingEntity);
                int i = 0;
                for (int j = 0; j < size; j++) {
                    ItemStack stack = provider.getStack(livingEntity, j);
                    if (stack.isEmpty()) {
                        continue;
                    }
                    data.putInt(I_ID + i, ITEM.getRawId(stack.getItem()));
                    data.putInt(I_COUNT + i, stack.getCount());
                    CompoundTag nbt = stack.getTag();
                    data.put(I_NBT + i, nbt == null || !config().entityInventory.isNbt() ? EMPTY_TAG : nbt);
                    i++;
                }
                data.putInt(I_SIZE, i);
                return true;
            } catch (Exception e) {
                errorData(ENTITY_INVENTORY, livingEntity, e);
                return false;
            }
        }

    }

}
