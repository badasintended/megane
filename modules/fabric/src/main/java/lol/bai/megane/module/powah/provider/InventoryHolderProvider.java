package lol.bai.megane.module.powah.provider;

import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.ItemData;
import net.minecraft.world.level.block.entity.BlockEntity;
import owmii.powah.lib.block.IInventoryHolder;

public class InventoryHolderProvider implements IDataProvider<BlockEntity> {

    @Override
    public void appendData(IDataWriter data, IServerAccessor<BlockEntity> accessor, IPluginConfig config) {
        data.add(ItemData.class, res -> {
            var inventory = ((IInventoryHolder) accessor.getTarget()).getInventory();
            res.add(ItemData.of(config).add(inventory.getStacks()));

            res.block();
        });
    }

}
