package badasintended.megane.impl;

import badasintended.megane.api.MeganeModule;
import badasintended.megane.api.provider.EnergyProvider;
import badasintended.megane.api.registry.MeganeRegistrar;
import dev.technici4n.fasttransferlib.api.energy.EnergyIo;

public class FastTransferLib implements MeganeModule {

    @Override
    public void register(MeganeRegistrar registrar) {
        registrar.energy(1100, EnergyIo.class, EnergyProvider.of(
            EnergyIo::getEnergy,
            EnergyIo::getEnergyCapacity
        ));
    }

}
