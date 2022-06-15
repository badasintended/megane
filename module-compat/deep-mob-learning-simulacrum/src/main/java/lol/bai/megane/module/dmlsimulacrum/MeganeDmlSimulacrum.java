package lol.bai.megane.module.dmlsimulacrum;

import io.github.projectet.dmlSimulacrum.block.entity.SimulationChamberEntity;
import io.github.projectet.dmlSimulacrum.dmlSimulacrum;
import lol.bai.megane.api.MeganeModule;
import lol.bai.megane.api.registry.ClientRegistrar;
import lol.bai.megane.api.registry.CommonRegistrar;
import lol.bai.megane.module.dmlsimulacrum.provider.SimulationChamberEnergyProvider;
import lol.bai.megane.module.dmlsimulacrum.provider.SimulationChamberProgressProvider;

@SuppressWarnings("unused")
public class MeganeDmlSimulacrum implements MeganeModule {

    @Override
    public void registerCommon(CommonRegistrar registrar) {
        registrar.addEnergy(SimulationChamberEntity.class, new SimulationChamberEnergyProvider());

        registrar.addProgress(SimulationChamberEntity.class, new SimulationChamberProgressProvider());
    }

    @Override
    public void registerClient(ClientRegistrar registrar) {
        registrar.addEnergyInfo(dmlSimulacrum.MOD_ID, 0xC01414, "E");
    }

}
