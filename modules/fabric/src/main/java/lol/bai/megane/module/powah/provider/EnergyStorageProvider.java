package lol.bai.megane.module.powah.provider;

import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.EnergyData;
import owmii.powah.lib.block.AbstractEnergyStorage;

public class EnergyStorageProvider implements IDataProvider<AbstractEnergyStorage<?, ?>> {

    @Override
    public void appendData(IDataWriter data, IServerAccessor<AbstractEnergyStorage<?, ?>> accessor, IPluginConfig config) {
        data.add(EnergyData.class, res -> {
            var energy = accessor.getTarget().getEnergy();
            res.add(EnergyData.of(energy.getStored(), energy.getCapacity()));
        });
    }

}
