package lol.bai.megane.module.ae2.provider;

import appeng.blockentity.storage.SkyStoneTankBlockEntity;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.FluidData;

@SuppressWarnings("UnstableApiUsage")
public class SkyStoneTankProvider implements IDataProvider<SkyStoneTankBlockEntity> {

    @Override
    public void appendData(IDataWriter data, IServerAccessor<SkyStoneTankBlockEntity> accessor, IPluginConfig config) {
        data.add(FluidData.class, res -> {
            var storage = accessor.getTarget().getStorage();
            var variant = storage.variant;

            res.add(FluidData.of(FluidData.Unit.DROPLETS, 1)
                .add(variant.getFluid(), variant.getNbt(), storage.amount, storage.getCapacity()));
        });
    }

}
