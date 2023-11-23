package lol.bai.megane.module.powah.provider;

import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.FluidData;
import net.minecraft.world.level.block.entity.BlockEntity;
import owmii.powah.lib.block.ITankHolder;
import owmii.powah.lib.logistics.fluid.Tank;

public class TankHolderProvider implements IDataProvider<BlockEntity> {

    @Override
    public void appendData(IDataWriter data, IServerAccessor<BlockEntity> accessor, IPluginConfig config) {
        data.add(FluidData.class, res -> {
            var tank = ((ITankHolder) accessor.getTarget()).getTank();
            addTank(res, tank);
        });
    }

    public static void addTank(IDataWriter.Result<FluidData> res, Tank tank) {
        var size = tank.getTanks();
        var fluidData = FluidData.of(FluidData.Unit.DROPLETS, size);

        for (int i = 0; i < size; i++) {
            var stack = tank.getFluidInTank(i);
            fluidData.add(stack.getFluid(), stack.getTag(), stack.getAmount(), tank.getTankCapacity(i));
        }

        res.add(fluidData);
    }

}
