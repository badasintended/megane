package lol.bai.megane.module.dmlsimulacrum;

import dev.nathanpb.dml.DeepMobLearningKt;
import dev.nathanpb.dml.simulacrum.block.chamber.BlockEntitySimulationChamber;
import lol.bai.megane.module.dmlsimulacrum.provider.SimulationChamberProvider;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.data.EnergyData;

@SuppressWarnings("unused")
public class MeganeDmlSimulacrum implements IWailaPlugin {

    @Override
    public void register(IRegistrar registrar) {
        EnergyData.describe(DeepMobLearningKt.MOD_ID).color(0xC01414).unit("E");

        registrar.addBlockData(new SimulationChamberProvider(), BlockEntitySimulationChamber.class);
    }

}
