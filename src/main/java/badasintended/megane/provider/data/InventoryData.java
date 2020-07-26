package badasintended.megane.provider.data;

import mcp.mobius.waila.api.IServerDataProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import static badasintended.megane.MeganeUtils.key;

public class InventoryData implements IServerDataProvider<BlockEntity> {

    public static final InventoryData INSTANCE = new InventoryData();

    private InventoryData() {
    }

    @Override
    public void appendServerData(CompoundTag data, ServerPlayerEntity player, World world, BlockEntity blockEntity) {
        if (blockEntity == null) return;
        boolean hasInventory = Inventory.class.isAssignableFrom(blockEntity.getClass());
        data.putBoolean(key("hasInventory"), hasInventory);
        if (hasInventory) {
            Inventory inventory = (Inventory) blockEntity;
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
