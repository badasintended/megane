package lol.bai.megane.module.create.provider;

import com.simibubi.create.content.schematics.table.SchematicTableBlockEntity;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.ItemData;

public class SchematicTableProvider implements IDataProvider<SchematicTableBlockEntity> {

    @Override
    public void appendData(IDataWriter data, IServerAccessor<SchematicTableBlockEntity> accessor, IPluginConfig config) {
        data.add(ItemData.class, res -> {
            var inventory = accessor.getTarget().inventory;

            res.add(ItemData.of(config)
                .getter(inventory::getStackInSlot, inventory.getSlots()));
        });
    }

}
