package lol.bai.megane.module.ae2.provider;

import appeng.blockentity.networking.EnergyCellBlockEntity;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.EnergyData;

public class EnergyCellProvider implements IDataProvider<EnergyCellBlockEntity> {

    @Override
    public void appendData(IDataWriter data, IServerAccessor<EnergyCellBlockEntity> accessor, IPluginConfig config) {
        data.add(EnergyData.class, res -> {
            var target = accessor.getTarget();
            res.add(EnergyData.of(target.getAECurrentPower(), target.getAEMaxPower()));
        });
    }

}
