package lol.bai.megane.module.mekanism.provider;

import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.EnergyData;
import mekanism.api.energy.IStrictEnergyHandler;
import mekanism.common.capabilities.Capabilities;
import net.minecraft.world.level.block.entity.BlockEntity;

public class StrictEnergyProvider implements IDataProvider<BlockEntity> {

    public static void addEnergy(IDataWriter.Result<EnergyData> res, IStrictEnergyHandler handler) {
        var count = handler.getEnergyContainerCount();
        var stored = 0.0;
        var max = 0.0;
        for (var i = 0; i < count; i++) {
            stored += handler.getEnergy(i).doubleValue();
            max += handler.getMaxEnergy(i).doubleValue();
        }

        res.add(EnergyData.of(stored, max));
    }

    @Override
    public void appendData(IDataWriter data, IServerAccessor<BlockEntity> accessor, IPluginConfig config) {
        data.add(EnergyData.class, res -> accessor.getTarget()
            .getCapability(Capabilities.STRICT_ENERGY)
            .ifPresent(handler -> addEnergy(res, handler)));
    }

}
