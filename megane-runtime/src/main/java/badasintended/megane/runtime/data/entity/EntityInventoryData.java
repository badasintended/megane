package badasintended.megane.runtime.data.entity;

import badasintended.megane.api.provider.InventoryProvider;
import badasintended.megane.runtime.data.Appender;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import static badasintended.megane.api.registry.TooltipRegistry.ENTITY_INVENTORY;
import static badasintended.megane.runtime.util.RuntimeUtils.errorData;
import static badasintended.megane.util.MeganeUtils.config;
import static badasintended.megane.util.MeganeUtils.key;

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
                if (provider == null || !provider.hasInventory(livingEntity)) return false;

                data.putBoolean(key("hasInventory"), true);
                int size = provider.size(livingEntity);
                data.putInt(key("invSize"), size);

                for (int i = 0; i < size; i++) {
                    ItemStack stack = provider.getStack(livingEntity, i);
                    data.putInt(key("itemId" + i), Registry.ITEM.getRawId(stack.getItem()));
                    data.putInt(key("itemCount" + i), stack.getCount());
                    data.put(key("itemTag" + i), stack.getOrCreateTag());
                }
                return true;
            } catch (Exception e) {
                errorData(ENTITY_INVENTORY, livingEntity, e);
                return false;
            }
        }

    }

}
