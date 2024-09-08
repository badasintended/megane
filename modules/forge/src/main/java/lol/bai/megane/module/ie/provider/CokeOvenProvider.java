package lol.bai.megane.module.ie.provider;

import blusunrize.immersiveengineering.common.blocks.stone.CokeOvenBlockEntity;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.ProgressData;

public class CokeOvenProvider implements IDataProvider<CokeOvenBlockEntity> {

    @Override
    public void appendData(IDataWriter data, IServerAccessor<CokeOvenBlockEntity> accessor, IPluginConfig config) {
        data.add(ProgressData.class, res -> {
            var oven = accessor.getTarget().master();
            if (oven == null) return;

            var inventory = oven.getInventory();
            if (inventory == null) return;

            var processStep = (float) oven.getCurrentProcessesStep()[0];
            var processMax = (float) oven.getCurrentProcessesMax()[0];
            if (processStep == 0) return;

            res.add(ProgressData.ratio(processStep / processMax)
                .itemGetter(inventory::get)
                .input(0)
                .output(1));
        });
    }

}
