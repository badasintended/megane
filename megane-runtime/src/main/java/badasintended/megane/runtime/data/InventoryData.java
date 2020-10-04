package badasintended.megane.runtime.data;

import badasintended.megane.api.registry.InventoryTooltipRegistry;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import static badasintended.megane.api.registry.InventoryTooltipRegistry.get;
import static badasintended.megane.util.MeganeUtils.config;
import static badasintended.megane.util.MeganeUtils.key;

public class InventoryData extends BaseData {

    public static final InventoryData INSTANCE = new InventoryData();

    public InventoryData() {
        super(() -> config().inventory);
        appenders.add(new Registered());
        appenders.add(new Hopper());
    }

    public static class Registered implements Appender {

        @Override
        @SuppressWarnings({"rawtypes", "unchecked"})
        public boolean append(CompoundTag data, ServerPlayerEntity player, World world, BlockEntity blockEntity) {
            InventoryTooltipRegistry.Provider provider = get(blockEntity);
            if (provider == null || !provider.hasInventory(blockEntity)) return false;

            data.putBoolean(key("hasInventory"), true);
            int size = provider.size(blockEntity);
            data.putInt(key("invSize"), size);
            DefaultedList<ItemStack> stacks = DefaultedList.ofSize(size, ItemStack.EMPTY);

            for (int i = 0; i < size; i++) {
                stacks.set(i, provider.getStack(blockEntity, i));
            }
            CompoundTag invTag = Inventories.toTag(new CompoundTag(), stacks);

            data.put(key("inventory"), invTag);
            return true;
        }

    }

    public static class Hopper implements Appender {

        @Override
        public boolean append(CompoundTag data, ServerPlayerEntity player, World world, BlockEntity blockEntity) {
            Inventory inventory = HopperBlockEntity.getInventoryAt(world, blockEntity.getPos());
            if (inventory == null) return false;

            data.putBoolean(key("hasInventory"), true);
            int size = inventory.size();
            data.putInt(key("invSize"), size);
            DefaultedList<ItemStack> stacks = DefaultedList.ofSize(size, ItemStack.EMPTY);

            for (int i = 0; i < size; i++) {
                stacks.set(i, inventory.getStack(i));
            }
            CompoundTag invTag = Inventories.toTag(new CompoundTag(), stacks);

            data.put(key("inventory"), invTag);
            return true;
        }

    }

}
