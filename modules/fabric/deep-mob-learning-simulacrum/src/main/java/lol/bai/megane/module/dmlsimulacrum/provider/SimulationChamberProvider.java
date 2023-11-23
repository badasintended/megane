package lol.bai.megane.module.dmlsimulacrum.provider;

import dev.nathanpb.dml.simulacrum.block.chamber.BlockEntitySimulationChamber;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.EnergyData;
import mcp.mobius.waila.api.data.ProgressData;

public class SimulationChamberProvider implements IDataProvider<BlockEntitySimulationChamber> {

    @Override
    public void appendData(IDataWriter data, IServerAccessor<BlockEntitySimulationChamber> accessor, IPluginConfig config) {
        data.add(EnergyData.class, res -> {
            var storage = accessor.getTarget().getEnergyStorage();
            res.add(EnergyData.of(storage.getAmount(), storage.getCapacity()));
        });

        data.add(ProgressData.class, res -> {
            var target = accessor.getTarget();

            res.add(ProgressData
                .ratio(target.getPercentDone() / 100f)
                .itemGetter(target::getItem)
                .input(0, 1)
                .output(2, 3));
        });
    }

}
