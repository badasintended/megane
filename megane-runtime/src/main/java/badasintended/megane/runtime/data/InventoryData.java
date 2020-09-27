package badasintended.megane.runtime.data;

import mcp.mobius.waila.api.IServerDataProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import static badasintended.megane.util.MeganeUtils.config;
import static badasintended.megane.util.MeganeUtils.key;

public class InventoryData implements IServerDataProvider<BlockEntity> {

    public static final InventoryData INSTANCE = new InventoryData();

    @Override
    public void appendServerData(CompoundTag data, ServerPlayerEntity player, World world, BlockEntity blockEntity) {
        if (!config().inventory.isEnabled() || config().inventory.getBlacklist().contains(Registry.BLOCK.getId(blockEntity.getCachedState().getBlock()))) {
            return;
        }

        Inventory inventory = HopperBlockEntity.getInventoryAt(world, blockEntity.getPos());
        if (inventory != null) {
            data.putBoolean(key("hasInventory"), true);
            int size = inventory.size();
            data.putInt(key("invSize"), size);
            DefaultedList<ItemStack> stacks = DefaultedList.ofSize(size, ItemStack.EMPTY);

            for (int i = 0; i < size; i++) {
                stacks.set(i, inventory.getStack(i));
            }
            CompoundTag invTag = Inventories.toTag(new CompoundTag(), stacks);

            data.put(key("inventory"), invTag);
        }
    }

}
