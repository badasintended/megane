package lol.bai.megane.module.luggage.provider;

import com.gizmo.luggage.entity.LuggageEntity;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.ItemData;

public class LuggageProvider implements IDataProvider<LuggageEntity> {

    @Override
    public void appendData(IDataWriter data, IServerAccessor<LuggageEntity> accessor, IPluginConfig config) {
        data.add(ItemData.class, res -> res.add(ItemData.of(config)
            .vanilla(accessor.getTarget().getInventory())));
    }

}
