package lol.bai.megane.module.reborncore.provider;

import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.EnergyData;
import reborncore.common.powerSystem.PowerAcceptorBlockEntity;

public class PowerAcceptorProvider implements IDataProvider<PowerAcceptorBlockEntity> {

    @Override
    public void appendData(IDataWriter data, IServerAccessor<PowerAcceptorBlockEntity> accessor, IPluginConfig config) {
        data.add(EnergyData.class, res -> {
            var target = accessor.getTarget();
            res.add(EnergyData.of(target.getEnergy(), target.getMaxStoredPower()));
        });
    }

}
