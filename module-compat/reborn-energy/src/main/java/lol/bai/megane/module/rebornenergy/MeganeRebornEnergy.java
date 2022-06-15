package lol.bai.megane.module.rebornenergy;

import lol.bai.megane.api.MeganeModule;
import lol.bai.megane.api.registry.CommonRegistrar;
import lol.bai.megane.module.rebornenergy.provider.EnergyStorageEnergyProvider;
import team.reborn.energy.api.EnergyStorage;

public class MeganeRebornEnergy implements MeganeModule {

    @Override
    public void registerCommon(CommonRegistrar registrar) {
        registrar.addEnergy(1200, EnergyStorage.class, new EnergyStorageEnergyProvider());
    }

}
