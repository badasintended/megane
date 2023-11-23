package lol.bai.megane.module.alloyforgery.provider;

import lol.bai.megane.module.alloyforgery.mixin.AccessorForgeControllerBlockEntity;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.EnergyData;
import mcp.mobius.waila.api.data.ProgressData;
import wraith.alloyforgery.block.ForgeControllerBlockEntity;

public class ForgeControllerProvider implements IDataProvider<ForgeControllerBlockEntity> {

    @Override
    public void appendData(IDataWriter data, IServerAccessor<ForgeControllerBlockEntity> accessor, IPluginConfig config) {
        var target = accessor.getTarget();
        var access = (AccessorForgeControllerBlockEntity) target;

        data.add(EnergyData.class, res -> {
            res.add(EnergyData.of(access.getFuel(), access.getForgeDefinition().fuelCapacity()));
        });

        data.add(ProgressData.class, res -> {
            var progressData = ProgressData.ratio((float) target.getCurrentSmeltTime() / access.getForgeDefinition().maxSmeltTime());
            progressData.itemGetter(target::getItem);
            progressData.input(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 11);
            progressData.output(10);
            res.add(progressData);
        });
    }

}
