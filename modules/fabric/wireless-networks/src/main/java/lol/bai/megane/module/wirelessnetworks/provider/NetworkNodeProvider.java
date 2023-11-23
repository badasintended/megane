package lol.bai.megane.module.wirelessnetworks.provider;

import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.EnergyData;
import me.steven.wirelessnetworks.blockentity.NetworkNodeBlockEntity;

@SuppressWarnings("SimplifyOptionalCallChains")
public class NetworkNodeProvider implements IDataProvider<NetworkNodeBlockEntity> {

    @Override
    public void appendData(IDataWriter data, IServerAccessor<NetworkNodeBlockEntity> accessor, IPluginConfig config) {
        data.add(EnergyData.class, res -> {
            var network = accessor.getTarget().getNetwork().orElse(null);

            if (network != null) {
                res.add(EnergyData.of(network.getAmount(), network.getCapacity()));
            }

            res.block();
        });
    }

}
