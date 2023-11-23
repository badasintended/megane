package lol.bai.megane.module.powah.provider;

import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.ProgressData;
import owmii.powah.block.furnator.FurnatorTile;

public class FurnatorProvider implements IDataProvider<FurnatorTile> {

    @Override
    public void appendData(IDataWriter data, IServerAccessor<FurnatorTile> accessor, IPluginConfig config) {
        data.add(ProgressData.class, res -> {
            var furnator = accessor.getTarget();
            var carbon = furnator.getCarbon();

            res.add(ProgressData.ratio(carbon.subSized())
                .itemGetter(furnator.getInventory()::getStackInSlot)
                .input(1));
        });
    }

}
