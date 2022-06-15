package lol.bai.megane.module.dmlsimulacrum.provider;

import io.github.projectet.dmlSimulacrum.block.entity.SimulationChamberEntity;
import lol.bai.megane.api.provider.EnergyProvider;

public class SimulationChamberEnergyProvider extends EnergyProvider<SimulationChamberEntity> {

    @Override
    public long getStored() {
        return getObject().energyStorage.getAmount();
    }

    @Override
    public long getMax() {
        return getObject().energyStorage.getCapacity();
    }

}
