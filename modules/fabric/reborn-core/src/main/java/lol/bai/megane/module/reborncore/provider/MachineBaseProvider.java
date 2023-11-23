package lol.bai.megane.module.reborncore.provider;

import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.FluidData;
import reborncore.common.blockentity.MachineBaseBlockEntity;

public class MachineBaseProvider implements IDataProvider<MachineBaseBlockEntity> {

    @Override
    public void appendData(IDataWriter data, IServerAccessor<MachineBaseBlockEntity> accessor, IPluginConfig config) {
        data.add(FluidData.class, res -> {
            var tank = accessor.getTarget().getTank();

            if (tank != null) res.add(FluidData.of(FluidData.Unit.DROPLETS, 1)
                .add(tank.getFluid(), tank.getFluidInstance().getTag(), tank.getAmount(), tank.getCapacity()));

            res.block();
        });
    }

}
