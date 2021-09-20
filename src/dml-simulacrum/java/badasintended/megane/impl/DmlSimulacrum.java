package badasintended.megane.impl;

import badasintended.megane.api.MeganeModule;
import badasintended.megane.api.provider.EnergyProvider;
import badasintended.megane.api.provider.ProgressProvider;
import badasintended.megane.api.registry.MeganeClientRegistrar;
import badasintended.megane.api.registry.MeganeRegistrar;
import badasintended.megane.impl.util.A;
import io.github.projectet.dmlSimulacrum.block.entity.SimulationChamberEntity;
import io.github.projectet.dmlSimulacrum.inventory.ImplementedInventory;

public class DmlSimulacrum implements MeganeModule {

    @Override
    public void register(MeganeRegistrar registrar) {
        registrar.energy(SimulationChamberEntity.class, EnergyProvider.of(
            t -> t.energyStorage.amount,
            t -> t.energyStorage.capacity
        ));

        int[] output = {2, 3};
        registrar.progress(SimulationChamberEntity.class, ProgressProvider.of(
            t -> A.A_01,
            t -> output,
            ImplementedInventory::getStack,
            t -> t.percentDone
        ));
    }

    @Override
    public void registerClient(MeganeClientRegistrar registrar) {
        registrar.energy("dmlsimulacrum", 0xc01414, "E");
    }

}
