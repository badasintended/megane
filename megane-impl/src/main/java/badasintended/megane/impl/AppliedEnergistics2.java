package badasintended.megane.impl;

import appeng.api.networking.energy.IAEPowerStorage;
import appeng.tile.AEBaseBlockEntity;
import appeng.tile.misc.InscriberBlockEntity;
import badasintended.megane.api.MeganeEntrypoint;
import badasintended.megane.api.registry.EnergyTooltipRegistry;
import badasintended.megane.api.registry.ProgressTooltipRegistry;
import badasintended.megane.impl.util.Arrays;

public class AppliedEnergistics2 implements MeganeEntrypoint {

    private static final String[] DEP = new String[]{"appliedenergistics2"};

    @Override
    public String[] dependencies() {
        return DEP;
    }

    @Override
    public void initialize() {
        /*
        EnergyTooltipRegistry.register(AEBasePoweredBlockEntity.class, EnergyTooltipRegistry.Provider.of(
            IAEPowerStorage::getAECurrentPower, IAEPowerStorage::getAEMaxPower
        ));

        EnergyTooltipRegistry.register(EnergyCellBlockEntity.class, EnergyTooltipRegistry.Provider.of(
            IAEPowerStorage::getAECurrentPower, IAEPowerStorage::getAEMaxPower
        ));

        EnergyTooltipRegistry.register(CreativeEnergyCellBlockEntity.class, EnergyTooltipRegistry.Provider.of(
            IAEPowerStorage::getAECurrentPower, IAEPowerStorage::getAEMaxPower
        ));
         */

        EnergyTooltipRegistry.register(AEBaseBlockEntity.class, EnergyTooltipRegistry.Provider.of(
            t -> t instanceof IAEPowerStorage,
            t -> ((IAEPowerStorage) t).getAECurrentPower(),
            t -> ((IAEPowerStorage) t).getAEMaxPower()
        ));

        ProgressTooltipRegistry.register(InscriberBlockEntity.class, ProgressTooltipRegistry.Provider.of(
            t -> Arrays.A_012, t -> Arrays.A_3, (t, i) -> t.getInternalInventory().getInvStack(i),
            InscriberBlockEntity::getProcessingTime
        ));
    }

}
