package lol.bai.megane.module.dmlsimulacrum;

import dev.nathanpb.dml.DeepMobLearningKt;
import dev.nathanpb.dml.simulacrum.block.chamber.BlockEntitySimulationChamber;
import lol.bai.megane.api.MeganeModule;
import lol.bai.megane.api.registry.ClientRegistrar;
import lol.bai.megane.api.registry.CommonRegistrar;
import lol.bai.megane.module.dmlsimulacrum.provider.SimulationChamberEnergyProvider;
import lol.bai.megane.module.dmlsimulacrum.provider.SimulationChamberProgressProvider;

@SuppressWarnings("unused")
public class MeganeDmlSimulacrum implements MeganeModule {

    @Override
    public void registerCommon(CommonRegistrar registrar) {
        registrar.addEnergy(BlockEntitySimulationChamber.class, new SimulationChamberEnergyProvider());

        registrar.addProgress(BlockEntitySimulationChamber.class, new SimulationChamberProgressProvider());
    }

    @Override
    public void registerClient(ClientRegistrar registrar) {
        registrar.addEnergyInfo(DeepMobLearningKt.MOD_ID, 0xC01414, "E");
    }

}
