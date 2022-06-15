package lol.bai.megane.module.rebornenergy.provider;

import lol.bai.megane.api.provider.EnergyProvider;
import team.reborn.energy.api.EnergyStorage;

public class EnergyStorageEnergyProvider extends EnergyProvider<EnergyStorage> {

    @Override
    public long getStored() {
        return getObject().getAmount();
    }

    @Override
    public long getMax() {
        return getObject().getCapacity();
    }

}
