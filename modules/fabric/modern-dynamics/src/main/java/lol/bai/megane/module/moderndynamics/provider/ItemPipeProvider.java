package lol.bai.megane.module.moderndynamics.provider;

import com.google.common.primitives.Ints;
import dev.technici4n.moderndynamics.network.item.TravelingItem;
import dev.technici4n.moderndynamics.pipe.ItemPipeBlockEntity;
import lol.bai.megane.module.moderndynamics.mixin.AccessorItemHost;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.ItemData;

@SuppressWarnings("UnstableApiUsage")
public class ItemPipeProvider implements IDataProvider<ItemPipeBlockEntity> {

    @Override
    public void appendData(IDataWriter data, IServerAccessor<ItemPipeBlockEntity> accessor, IPluginConfig config) {
        data.add(ItemData.class, res -> {
            var host = (AccessorItemHost) accessor.getTarget().getHosts()[0];
            var items = host.getTravelingItems();
            var itemData = ItemData.of(config).ensureSpace(items.size());

            for (TravelingItem item : items) {
                itemData.add(item.variant.toStack(Ints.saturatedCast(item.amount)));
            }
        });
    }

}
