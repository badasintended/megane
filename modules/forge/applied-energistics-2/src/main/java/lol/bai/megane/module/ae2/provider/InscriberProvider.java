package lol.bai.megane.module.ae2.provider;

import appeng.blockentity.misc.InscriberBlockEntity;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.ProgressData;

public class InscriberProvider implements IDataProvider<InscriberBlockEntity> {

    @Override
    public void appendData(IDataWriter data, IServerAccessor<InscriberBlockEntity> accessor, IPluginConfig config) {
        data.add(ProgressData.class, res -> {
            var target = accessor.getTarget();

            res.add(ProgressData.ratio(target.getProcessingTime() / 100f)
                .itemGetter(target.getInternalInventory()::getStackInSlot)
                .input(0, 1, 2)
                .output(3));
        });
    }

}
