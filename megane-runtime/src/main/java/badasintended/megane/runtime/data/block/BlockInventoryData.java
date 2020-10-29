package badasintended.megane.runtime.data.block;

import badasintended.megane.api.provider.InventoryProvider;
import badasintended.megane.runtime.data.Appender;
import net.minecraft.block.entity.*;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import static badasintended.megane.api.registry.TooltipRegistry.BLOCK_INVENTORY;
import static badasintended.megane.runtime.util.RuntimeUtils.errorData;
import static badasintended.megane.util.MeganeUtils.config;
import static badasintended.megane.util.MeganeUtils.key;

public class BlockInventoryData extends BlockData {

    public BlockInventoryData() {
        super(() -> config().inventory);
        appenders.add(new Registered());
        appenders.add(new Hopper());
    }

    public static class Registered implements Appender<BlockEntity> {

        @Override
        @SuppressWarnings({"rawtypes", "unchecked"})
        public boolean append(CompoundTag data, ServerPlayerEntity player, World world, BlockEntity blockEntity) {
            try {
                InventoryProvider provider = BLOCK_INVENTORY.get(blockEntity);
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
            } catch (Exception e) {
                errorData(BLOCK_INVENTORY, blockEntity, e);
                return false;
            }
        }

    }

    public static class Hopper implements Appender<BlockEntity> {

        @Override
        public boolean append(CompoundTag data, ServerPlayerEntity player, World world, BlockEntity blockEntity) {
            Inventory inventory;
            if (blockEntity instanceof EnderChestBlockEntity) {
                inventory = player.getEnderChestInventory();
            } else {
                inventory = HopperBlockEntity.getInventoryAt(world, blockEntity.getPos());
            }
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
