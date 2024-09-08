package lol.bai.megane.module.create.provider;

import com.simibubi.create.content.kinetics.deployer.DeployerBlockEntity;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.ItemData;

public class DeployerProvider implements IDataProvider<DeployerBlockEntity> {

    @Override
    public void appendData(IDataWriter data, IServerAccessor<DeployerBlockEntity> accessor, IPluginConfig config) {
        data.add(ItemData.class, res -> res.add(ItemData.of(config)
            .add(accessor.getTarget().getPlayer().getMainHandItem())));
    }

}
