package lol.bai.megane.module.ie.provider;

import blusunrize.immersiveengineering.common.util.inventory.IIEInventory;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.ItemData;
import net.minecraft.world.level.block.entity.BlockEntity;

public class IEInventoryProvider implements IDataProvider<BlockEntity> {

    @Override
    public void appendData(IDataWriter data, IServerAccessor<BlockEntity> accessor, IPluginConfig config) {
        data.add(ItemData.class, res -> {
            var target = (IIEInventory) accessor.getTarget();

            var inventory = target.getInventory();
            if (inventory == null) return;

            res.add(ItemData.of(config).getter(inventory::get, inventory.size()));
        });
    }

}
