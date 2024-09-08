package lol.bai.megane.module.reborncore.provider;

import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.FluidData;
import mcp.mobius.waila.api.fabric.FabricFluidData;
import reborncore.common.blockentity.MachineBaseBlockEntity;

public class MachineBaseProvider implements IDataProvider<MachineBaseBlockEntity> {

    @Override
    public void appendData(IDataWriter data, IServerAccessor<MachineBaseBlockEntity> accessor, IPluginConfig config) {
        data.add(FluidData.class, res -> {
            var tank = accessor.getTarget().getTank();

            if (tank != null) res.add(FabricFluidData.of(1)
                .add(tank.getResource(), tank.getAmount(), tank.getCapacity()));

            res.block();
        });
    }

}
