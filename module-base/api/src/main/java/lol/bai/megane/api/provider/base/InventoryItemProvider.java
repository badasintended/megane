package lol.bai.megane.api.provider.base;

import lol.bai.megane.api.provider.ItemProvider;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class InventoryItemProvider<T> extends ItemProvider<T> {

    private Inventory inventory;

    @Nullable
    protected abstract Inventory getInventory();

    @Override
    protected void init() {
        this.inventory = getInventory();
    }

    @Override
    public boolean hasItems() {
        return inventory != null;
    }

    @Override
    public int getSlotCount() {
        return inventory.size();
    }

    @NotNull
    @Override
    public ItemStack getStack(int slot) {
        return inventory.getStack(slot);
    }

}
