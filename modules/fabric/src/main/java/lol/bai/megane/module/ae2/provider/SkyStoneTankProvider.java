package lol.bai.megane.module.ae2.provider;

import appeng.blockentity.storage.SkyStoneTankBlockEntity;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.FluidData;
import mcp.mobius.waila.api.fabric.FabricFluidData;

@SuppressWarnings("UnstableApiUsage")
public class SkyStoneTankProvider implements IDataProvider<SkyStoneTankBlockEntity> {

    @Override
    public void appendData(IDataWriter data, IServerAccessor<SkyStoneTankBlockEntity> accessor, IPluginConfig config) {
        data.add(FluidData.class, res -> {
            var storage = accessor.getTarget().getStorage();

            res.add(FabricFluidData.of(1)
                .add(storage.variant, storage.amount, storage.getCapacity()));
        });
    }

}
