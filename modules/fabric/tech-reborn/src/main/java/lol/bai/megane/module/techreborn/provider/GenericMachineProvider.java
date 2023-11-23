package lol.bai.megane.module.techreborn.provider;

import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.ProgressData;
import techreborn.blockentity.machine.GenericMachineBlockEntity;

public class GenericMachineProvider implements IDataProvider<GenericMachineBlockEntity> {

    @Override
    public void appendData(IDataWriter data, IServerAccessor<GenericMachineBlockEntity> accessor, IPluginConfig config) {
        data.add(ProgressData.class, res -> {
            var target = accessor.getTarget();
            var crafter = target.crafter;

            if (crafter != null) res.add(ProgressData
                .ratio(target.getProgressScaled(100) / 100f)
                .itemGetter(target.getInventory()::getItem)
                .input(crafter.inputSlots)
                .output(crafter.inputSlots));
        });
    }

}
