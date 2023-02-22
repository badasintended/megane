package lol.bai.megane.module.powah.provider;

import owmii.powah.block.reactor.ReactorPartTile;
import owmii.powah.block.reactor.ReactorTile;
import owmii.powah.lib.logistics.inventory.Inventory;
import owmii.powah.lib.util.Ticker;

public class ReactorProgressProvider extends TickerProgressProvider<ReactorPartTile> {

    ReactorTile core;

    @Override
    protected void init() {
        this.core = getObject().core().orElse(null);
        super.init();
    }

    @Override
    protected int[] getInputSlots() {
        return new int[0];
    }

    @Override
    protected int[] getOutputSlots() {
        return new int[0];
    }

    @Override
    Ticker getTicker() {
        return core.fuel;
    }

    @Override
    Inventory getInventory() {
        return core.getInventory();
    }

    @Override
    public int getPercentage() {
        return (int) ticker.perCent();
    }

}
