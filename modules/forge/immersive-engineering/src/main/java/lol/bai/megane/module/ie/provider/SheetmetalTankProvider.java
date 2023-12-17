package lol.bai.megane.module.ie.provider;

import blusunrize.immersiveengineering.common.blocks.metal.SheetmetalTankBlockEntity;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.FluidData;

public class SheetmetalTankProvider implements IDataProvider<SheetmetalTankBlockEntity> {

    @Override
    public void appendData(IDataWriter data, IServerAccessor<SheetmetalTankBlockEntity> accessor, IPluginConfig config) {
        data.add(FluidData.class, res -> {
            var tank = accessor.getTarget().master();
            if (tank == null) return;

            var stack = tank.tank.getFluid();

            res.add(FluidData.of(FluidData.Unit.MILLIBUCKETS, 1)
                .add(stack.getFluid(), stack.getTag(), stack.getAmount(), tank.tank.getCapacity()));
        });
    }

}
