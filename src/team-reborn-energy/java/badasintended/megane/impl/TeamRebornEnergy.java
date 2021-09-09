package badasintended.megane.impl;

import badasintended.megane.api.MeganeModule;
import badasintended.megane.api.provider.EnergyProvider;
import badasintended.megane.api.registry.MeganeRegistrar;
import team.reborn.energy.api.EnergyStorage;

public class TeamRebornEnergy implements MeganeModule {

    @Override
    public void register(MeganeRegistrar registrar) {
        registrar.energy(1200, EnergyStorage.class, EnergyProvider.of(
            EnergyStorage::getAmount,
            EnergyStorage::getCapacity
        ));
    }

}
