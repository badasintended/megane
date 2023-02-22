package lol.bai.megane.module.powah.provider;

import lol.bai.megane.api.provider.base.SlotArrayProgressProvider;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import owmii.powah.lib.logistics.inventory.Inventory;
import owmii.powah.lib.util.Ticker;

public abstract class TickerProgressProvider<T> extends SlotArrayProgressProvider<T> {

    Ticker ticker;
    Inventory inventory;

    abstract Ticker getTicker();

    abstract Inventory getInventory();

    @Override
    protected void init() {
        this.ticker = getTicker();
        this.inventory = getInventory();
    }

    @Override
    public boolean hasProgress() {
        return ticker != null && inventory != null;
    }

    @Override
    protected @NotNull ItemStack getStack(int slot) {
        return inventory.getStackInSlot(slot);
    }

}
