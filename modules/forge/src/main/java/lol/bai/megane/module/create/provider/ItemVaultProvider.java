package lol.bai.megane.module.create.provider;

import com.simibubi.create.content.logistics.vault.ItemVaultBlockEntity;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.ItemData;

public class ItemVaultProvider implements IDataProvider<ItemVaultBlockEntity> {

    @Override
    public void appendData(IDataWriter data, IServerAccessor<ItemVaultBlockEntity> accessor, IPluginConfig config) {
        data.add(ItemData.class, res -> {
            var inventory = accessor.getTarget().getInventoryOfBlock();

            res.add(ItemData.of(config)
                .getter(inventory::getStackInSlot, inventory.getSlots()));
        });
    }

}
