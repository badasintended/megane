package badasintended.megane.impl;

import appeng.api.networking.energy.IAEPowerStorage;
import appeng.blockentity.AEBaseInvBlockEntity;
import appeng.blockentity.misc.InscriberBlockEntity;
import appeng.blockentity.misc.VibrationChamberBlockEntity;
import badasintended.megane.api.MeganeModule;
import badasintended.megane.api.provider.EnergyProvider;
import badasintended.megane.api.provider.InventoryProvider;
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
            .inventory(AEBaseInvBlockEntity.class, InventoryProvider.of(
                t -> t.getInternalInventory().size(),
                (t, i) -> t.getInternalInventory().getStackInSlot(i)
            ))
            .progress(InscriberBlockEntity.class, ProgressProvider.of(
                t -> A.A_012,
                t -> A.A_3,
                (t, i) -> t.getInternalInventory().getStackInSlot(i),
                InscriberBlockEntity::getProcessingTime))
            .progress(VibrationChamberBlockEntity.class, ProgressProvider.of(
                t -> A.A_0,
                t -> A.A,
                (t, i) -> t.getInternalInventory().getStackInSlot(i),
                t -> 100 - (int) (t.getBurnTime() / t.getMaxBurnTime() * 100)
            ));
    }

    @Override
    public void registerClient(MeganeClientRegistrar registrar) {
        registrar.energy("ae2", 0x64099F, "AE");
    }

}
