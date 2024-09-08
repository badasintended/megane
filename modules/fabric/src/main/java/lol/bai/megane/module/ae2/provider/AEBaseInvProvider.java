package lol.bai.megane.module.ae2.provider;

import appeng.blockentity.AEBaseInvBlockEntity;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.ItemData;

public class AEBaseInvProvider implements IDataProvider<AEBaseInvBlockEntity> {

    @Override
    public void appendData(IDataWriter data, IServerAccessor<AEBaseInvBlockEntity> accessor, IPluginConfig config) {
        data.add(ItemData.class, res -> {
            var inventory = accessor.getTarget().getInternalInventory();
            res.add(ItemData.of(config).getter(inventory::getStackInSlot, inventory.size()));
        });
    }

}
