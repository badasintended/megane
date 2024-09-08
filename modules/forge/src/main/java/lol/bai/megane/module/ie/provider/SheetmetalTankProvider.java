package lol.bai.megane.module.ie.provider;

import blusunrize.immersiveengineering.common.blocks.metal.SheetmetalTankBlockEntity;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.FluidData;
import mcp.mobius.waila.api.forge.ForgeFluidData;

public class SheetmetalTankProvider implements IDataProvider<SheetmetalTankBlockEntity> {

    @Override
    public void appendData(IDataWriter data, IServerAccessor<SheetmetalTankBlockEntity> accessor, IPluginConfig config) {
        data.add(FluidData.class, res -> {
            var tank = accessor.getTarget().master();
            if (tank == null) return;

            res.add(ForgeFluidData.of(1)
                .add(tank.tank.getFluid(), tank.tank.getCapacity()));
        });
    }

}
