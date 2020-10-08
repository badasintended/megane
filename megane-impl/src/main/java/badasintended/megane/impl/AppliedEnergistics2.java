package badasintended.megane.impl;

import appeng.api.networking.energy.IAEPowerStorage;
import appeng.tile.AEBaseBlockEntity;
import appeng.tile.misc.InscriberBlockEntity;
import badasintended.megane.api.MeganeEntrypoint;
import badasintended.megane.api.provider.EnergyProvider;
import badasintended.megane.api.provider.ProgressProvider;
import badasintended.megane.impl.util.A;

import static badasintended.megane.api.registry.TooltipRegistry.ENERGY;
import static badasintended.megane.api.registry.TooltipRegistry.PROGRESS;

public class AppliedEnergistics2 implements MeganeEntrypoint {

    private static final String[] DEP = new String[]{"appliedenergistics2"};

    @Override
    public String[] dependencies() {
        return DEP;
    }

    @Override
    public void initialize() {
        ENERGY.register(AEBaseBlockEntity.class, EnergyProvider.of(
            t -> t instanceof IAEPowerStorage,
            t -> ((IAEPowerStorage) t).getAECurrentPower(),
            t -> ((IAEPowerStorage) t).getAEMaxPower()
        ));

        PROGRESS.register(InscriberBlockEntity.class, ProgressProvider.of(
            t -> A.A_012, t -> A.A_3, (t, i) -> t.getInternalInventory().getInvStack(i),
            InscriberBlockEntity::getProcessingTime
        ));
    }

}
