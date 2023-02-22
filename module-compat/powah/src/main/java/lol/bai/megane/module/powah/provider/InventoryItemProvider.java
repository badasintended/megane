package lol.bai.megane.module.powah.provider;

import java.util.function.Function;

import lol.bai.megane.api.provider.ItemProvider;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import owmii.powah.lib.logistics.inventory.Inventory;

public class InventoryItemProvider<T> extends ItemProvider<T> {

    final Function<T, Inventory> getter;

    Inventory inventory;

    public InventoryItemProvider(Function<T, Inventory> getter) {
        this.getter = getter;
    }

    @Override
    protected void init() {
        this.inventory = getter.apply(getObject());
    }

    @Override
    public boolean hasItems() {
        return inventory != null;
    }

    @Override
    public int getSlotCount() {
        return inventory.getSlots();
    }

    @Override
    public @NotNull ItemStack getStack(int slot) {
        return inventory.getStackInSlot(slot);
    }

}
