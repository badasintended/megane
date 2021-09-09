package badasintended.megane.impl;

import badasintended.megane.api.MeganeModule;
import badasintended.megane.api.provider.EnergyProvider;
import badasintended.megane.api.registry.MeganeRegistrar;
import reborncore.common.powerSystem.PowerAcceptorBlockEntity;

public class RebornCore implements MeganeModule {

    @Override
    public void register(MeganeRegistrar registrar) {
        registrar
            .energy(1100, PowerAcceptorBlockEntity.class, EnergyProvider.of(
                PowerAcceptorBlockEntity::getEnergy,
                PowerAcceptorBlockEntity::getMaxStoredPower
            ));
    }

}
