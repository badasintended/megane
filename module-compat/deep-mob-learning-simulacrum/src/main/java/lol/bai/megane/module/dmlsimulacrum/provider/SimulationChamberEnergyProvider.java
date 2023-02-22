package lol.bai.megane.module.dmlsimulacrum.provider;

import dev.nathanpb.dml.simulacrum.block.chamber.BlockEntitySimulationChamber;
import lol.bai.megane.api.provider.EnergyProvider;

public class SimulationChamberEnergyProvider extends EnergyProvider<BlockEntitySimulationChamber> {

    @Override
    public long getStored() {
        return getObject().getEnergyStorage().getAmount();
    }

    @Override
    public long getMax() {
        return getObject().getEnergyStorage().getCapacity();
    }

}
