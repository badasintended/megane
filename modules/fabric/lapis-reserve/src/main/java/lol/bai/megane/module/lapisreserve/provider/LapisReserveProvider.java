package lol.bai.megane.module.lapisreserve.provider;

import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.ItemData;
import net.minecraft.world.level.block.entity.EnchantmentTableBlockEntity;
import sf.ssf.sfort.PlayerInterface;

public class LapisReserveProvider implements IDataProvider<EnchantmentTableBlockEntity> {

    @Override
    public void appendData(IDataWriter data, IServerAccessor<EnchantmentTableBlockEntity> accessor, IPluginConfig config) {
        data.add(ItemData.class, res -> {
            var reserve = ((PlayerInterface) accessor.getPlayer().getInventory()).getLapisreserve();
            if (reserve.isEmpty()) return;

            res.add(ItemData.of(config).add(reserve));
        });
    }

}
