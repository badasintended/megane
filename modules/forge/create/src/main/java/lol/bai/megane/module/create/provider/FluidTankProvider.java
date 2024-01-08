package lol.bai.megane.module.create.provider;

import com.simibubi.create.content.fluids.tank.CreativeFluidTankBlockEntity;
import com.simibubi.create.content.fluids.tank.FluidTankBlockEntity;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.FluidData;
import mcp.mobius.waila.api.forge.ForgeFluidData;

public class FluidTankProvider implements IDataProvider<FluidTankBlockEntity> {

    @Override
    public void appendData(IDataWriter data, IServerAccessor<FluidTankBlockEntity> accessor, IPluginConfig config) {
        data.add(FluidData.class, res -> {
            var creative = accessor.getTarget() instanceof CreativeFluidTankBlockEntity;
            var tank = accessor.getTarget().getControllerBE().getTankInventory();
            var stack = tank.getFluid();

            res.add(ForgeFluidData.of(1)
                .add(stack, creative ? Double.POSITIVE_INFINITY : stack.getAmount(), creative ? Double.POSITIVE_INFINITY : tank.getCapacity()));
        });
    }

}
