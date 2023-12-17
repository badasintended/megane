package lol.bai.megane.module.ie.provider;

import blusunrize.immersiveengineering.common.blocks.metal.SiloBlockEntity;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.ItemData;
import net.minecraftforge.items.ItemHandlerHelper;

public class SiloProvider implements IDataProvider<SiloBlockEntity> {

    @Override
    public void appendData(IDataWriter data, IServerAccessor<SiloBlockEntity> accessor, IPluginConfig config) {
        data.add(ItemData.class, res -> {
            var silo = accessor.getTarget().master();
            if (silo == null) return;

            res.add(ItemData.of(config)
                .add(ItemHandlerHelper.copyStackWithSize(silo.identStack, silo.storageAmount)));
        });
    }

}
