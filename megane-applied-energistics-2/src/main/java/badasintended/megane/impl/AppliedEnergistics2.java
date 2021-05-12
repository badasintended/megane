package badasintended.megane.impl;

import appeng.api.networking.energy.IAEPowerStorage;
import appeng.tile.AEBaseBlockEntity;
import appeng.tile.misc.InscriberBlockEntity;
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
            .energy(AEBaseBlockEntity.class, EnergyProvider.of(
                t -> t instanceof IAEPowerStorage,
                t -> ((IAEPowerStorage) t).getAECurrentPower(),
                t -> ((IAEPowerStorage) t).getAEMaxPower()))
            .progress(InscriberBlockEntity.class, ProgressProvider.of(
                t -> A.A_012,
                t -> A.A_3,
                (t, i) -> t.getInternalInventory().getInvStack(i),
                InscriberBlockEntity::getProcessingTime));
    }

    @Override
    public void registerClient(MeganeClientRegistrar registrar) {
        registrar.energy("appliedenergistics2", 0x64099F, "AE");
    }

}