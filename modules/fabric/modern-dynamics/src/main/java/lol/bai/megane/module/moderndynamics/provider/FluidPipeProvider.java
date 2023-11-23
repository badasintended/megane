package lol.bai.megane.module.moderndynamics.provider;

import dev.technici4n.moderndynamics.network.fluid.FluidHost;
import dev.technici4n.moderndynamics.pipe.FluidPipeBlockEntity;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.FluidData;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;

@SuppressWarnings("UnstableApiUsage")
public class FluidPipeProvider implements IDataProvider<FluidPipeBlockEntity> {

    @Override
    public void appendData(IDataWriter data, IServerAccessor<FluidPipeBlockEntity> accessor, IPluginConfig config) {
        data.add(FluidData.class, res -> {
            var host = (FluidHost) accessor.getTarget().getHosts()[0];
            var variant = host.getVariant();

            res.add(FluidData.of(FluidData.Unit.DROPLETS, 1)
                .add(variant.getFluid(), variant.getNbt(), host.getAmount(), FluidConstants.BUCKET));
        });
    }

}
