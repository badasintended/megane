package lol.bai.megane.module.powah.provider;

import owmii.powah.block.furnator.FurnatorTile;
import owmii.powah.lib.logistics.inventory.Inventory;
import owmii.powah.lib.util.Ticker;

public class FurnatorProgressProvider extends TickerProgressProvider<FurnatorTile> {

    static final int[] INPUT = {1};
    static final int[] OUTPUT = {};

    @Override
    protected int[] getInputSlots() {
        return INPUT;
    }

    @Override
    protected int[] getOutputSlots() {
        return OUTPUT;
    }

    @Override
    Ticker getTicker() {
        return getObject().getCarbon();
    }

    @Override
    Inventory getInventory() {
        return getObject().getInventory();
    }

    @Override
    public int getPercentage() {
        int percent = (int) ticker.perCent();
        return percent == 100 ? 0 : 100 - percent;
    }

}
