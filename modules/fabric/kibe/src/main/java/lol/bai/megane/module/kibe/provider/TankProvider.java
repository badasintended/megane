package lol.bai.megane.module.kibe.provider;

import java.util.function.Function;

import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.FluidData;
import mcp.mobius.waila.api.fabric.FabricFluidData;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;

@SuppressWarnings("UnstableApiUsage")
public class TankProvider<T> implements IDataProvider<T> {

    private final Function<T, SingleVariantStorage<FluidVariant>> tankGetter;

    public TankProvider(Function<T, SingleVariantStorage<FluidVariant>> tankGetter) {
        this.tankGetter = tankGetter;
    }

    @Override
    public void appendData(IDataWriter data, IServerAccessor<T> accessor, IPluginConfig config) {
        data.add(FluidData.class, res -> {
            var tank = tankGetter.apply(accessor.getTarget());

            res.add(FabricFluidData.of(1)
                .add(tank.getResource(), tank.getAmount(), tank.getCapacity()));
        });
    }

}
