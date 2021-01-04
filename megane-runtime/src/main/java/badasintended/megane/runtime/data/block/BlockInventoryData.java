package badasintended.megane.runtime.data.block;

import badasintended.megane.api.provider.InventoryProvider;
import badasintended.megane.runtime.data.Appender;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import static badasintended.megane.api.registry.TooltipRegistry.BLOCK_INVENTORY;
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

public class BlockInventoryData extends BlockData {

    public BlockInventoryData() {
        super(() -> config().inventory);
        appenders.add(new Pre());
        appenders.add(new Registered());
        appenders.add(new Hopper());
    }

    public static class Pre implements Appender<BlockEntity> {

        @Override
        public boolean append(CompoundTag data, ServerPlayerEntity player, World world, BlockEntity blockEntity) {
            data.putBoolean(I_SHOW, config().inventory.isItemCount());
            return false;
        }

    }

    public static class Registered implements Appender<BlockEntity> {

        @Override
        @SuppressWarnings({"rawtypes", "unchecked"})
        public boolean append(CompoundTag data, ServerPlayerEntity player, World world, BlockEntity blockEntity) {
            boolean forceRegistry = config().inventory.isForceRegistry();
            try {
                InventoryProvider provider = BLOCK_INVENTORY.get(blockEntity);
                if (provider == null || !provider.hasInventory(blockEntity))
                    return forceRegistry;

                data.putBoolean(I_HAS, true);
                int size = provider.size(blockEntity);
                int i = 0;
                for (int j = 0; j < size; j++) {
                    ItemStack stack = provider.getStack(blockEntity, j);
                    if (stack.isEmpty()) {
                        continue;
                    }
                    data.putInt(I_ID + i, ITEM.getRawId(stack.getItem()));
                    data.putInt(I_COUNT + i, stack.getCount());
                    CompoundTag nbt = stack.getTag();
                    data.put(I_NBT + i, nbt == null || !config().inventory.isNbt() ? EMPTY_TAG : nbt);
                    i++;
                }
                data.putInt(I_SIZE, i);
                return true;
            } catch (Exception e) {
                errorData(BLOCK_INVENTORY, blockEntity, e);
                return forceRegistry;
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
            if (inventory == null)
                return false;

            data.putBoolean(I_HAS, true);
            int size = inventory.size();
            int i = 0;
            for (int j = 0; j < size; j++) {
                ItemStack stack = inventory.getStack(j);
                if (stack.isEmpty()) {
                    continue;
                }
                data.putInt(I_ID + i, ITEM.getRawId(stack.getItem()));
                data.putInt(I_COUNT + i, stack.getCount());
                CompoundTag nbt = stack.getTag();
                data.put(I_NBT + i, nbt == null || !config().inventory.isNbt() ? EMPTY_TAG : nbt);
                i++;
            }
            data.putInt(I_SIZE, i);
            return true;
        }

    }

}
