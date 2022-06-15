package lol.bai.megane.module.ae2.provider;

import appeng.api.networking.energy.IAEPowerStorage;
import lol.bai.megane.api.provider.EnergyProvider;

public class PowerStorageEnergyProvider<T extends IAEPowerStorage> extends EnergyProvider<T> {

    @Override
    public long getStored() {
        return (long) getObject().getAECurrentPower();
    }

    @Override
    public long getMax() {
        return (long) getObject().getAEMaxPower();
    }

}
