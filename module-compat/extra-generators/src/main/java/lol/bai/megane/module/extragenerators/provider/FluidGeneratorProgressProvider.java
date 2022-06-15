package lol.bai.megane.module.extragenerators.provider;

import java.util.function.Function;

import io.github.lucaargolo.extragenerators.common.blockentity.AbstractGeneratorBlockEntity;
import io.github.lucaargolo.extragenerators.utils.FluidGeneratorFuel;
import io.github.lucaargolo.extragenerators.utils.SimpleSidedInventory;

@SuppressWarnings("rawtypes")
public class FluidGeneratorProgressProvider<T extends AbstractGeneratorBlockEntity> extends AbstractGeneratorProgressProvider<T> {

    private final Function<T, FluidGeneratorFuel> fuelGetter;
    private FluidGeneratorFuel fuel;

    public FluidGeneratorProgressProvider(Function<T, SimpleSidedInventory> inventoryGetter, Function<T, FluidGeneratorFuel> fuelGetter, int... inputSlots) {
        super(inventoryGetter, inputSlots);
        this.fuelGetter = fuelGetter;
    }

    @Override
    protected void init() {
        super.init();
        this.fuel = fuelGetter.apply(getObject());
    }

    @Override
    public boolean hasProgress() {
        return fuel != null;
    }

    @Override
    protected int getCurrentBurnTime() {
        return fuel.getCurrentBurnTime();
    }

    @Override
    protected int getBurnTime() {
        return fuel.getBurnTime();
    }

}
