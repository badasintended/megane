package lol.bai.megane.module.powah.provider;

import java.util.function.Function;

import lol.bai.megane.api.provider.EnergyProvider;
import owmii.powah.lib.logistics.energy.Energy;

public class EnergyEnergyProvider<T> extends EnergyProvider<T> {

    final Function<T, Energy> getter;

    Energy energy;

    public EnergyEnergyProvider(Function<T, Energy> getter) {
        this.getter = getter;
    }

    @Override
    protected void init() {
        this.energy = getter.apply(getObject());
    }

    @Override
    public boolean hasEnergy() {
        return energy != null;
    }

    @Override
    public long getStored() {
        return energy.getStored();
    }

    @Override
    public long getMax() {
        return energy.getMaxEnergyStored();
    }

}
