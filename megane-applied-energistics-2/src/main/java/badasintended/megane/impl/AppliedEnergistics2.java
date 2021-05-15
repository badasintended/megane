package badasintended.megane.impl;

import appeng.api.networking.energy.IAEPowerStorage;
import appeng.tile.misc.InscriberTileEntity;
import badasintended.megane.api.MeganeModule;
import badasintended.megane.api.provider.EnergyProvider;
import badasintended.megane.api.provider.ProgressProvider;
import badasintended.megane.api.registry.MeganeClientRegistrar;
import badasintended.megane.api.registry.MeganeRegistrar;
import badasintended.megane.impl.util.A;

public class AppliedEnergistics2 implements MeganeModule {

    @Override
    public void register(MeganeRegistrar registrar) {
        registrar
            .energy(IAEPowerStorage.class, EnergyProvider.of(
                IAEPowerStorage::getAECurrentPower,
                IAEPowerStorage::getAEMaxPower))
            .progress(InscriberTileEntity.class, ProgressProvider.of(
                t -> A.A_012,
                t -> A.A_3,
                (t, i) -> t.getInternalInventory().getInvStack(i),
                InscriberTileEntity::getProcessingTime));
    }

    @Override
    public void registerClient(MeganeClientRegistrar registrar) {
        registrar.energy("appliedenergistics2", 0x64099F, "AE");
    }

}
