package lol.bai.megane.module.create.provider;

import com.simibubi.create.content.kinetics.millstone.MillstoneBlockEntity;
import lol.bai.megane.module.create.mixin.AccessMillstoneProgressProvider;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.ItemData;
import mcp.mobius.waila.api.data.ProgressData;

public class MillstoneProvider implements IDataProvider<MillstoneBlockEntity> {

    @Override
    public void appendData(IDataWriter data, IServerAccessor<MillstoneBlockEntity> accessor, IPluginConfig config) {
        data.add(ItemData.class, res -> {
            var target = accessor.getTarget();

            res.add(ItemData.of(config)
                .getter(target.inputInv::getStackInSlot, target.inputInv.getSlots())
                .getter(target.outputInv::getStackInSlot, target.outputInv.getSlots()));
        });

        data.add(ProgressData.class, res -> {
            var target = (MillstoneBlockEntity & AccessMillstoneProgressProvider) accessor.getTarget();
            var recipe = target.megane_lastRecipe();
            if (recipe == null || target.timer <= 0) return;

            var progressData = ProgressData.ratio(1 - ((float) target.timer / recipe.getProcessingDuration()));

            progressData.ensureInputSpace(target.inputInv.getSlots());
            for (int i = 0; i < target.inputInv.getSlots(); i++) {
                progressData.input(target.inputInv.getStackInSlot(i));
            }

            progressData.ensureOutputSpace(target.outputInv.getSlots());
            for (int i = 0; i < target.outputInv.getSlots(); i++) {
                progressData.output(target.outputInv.getStackInSlot(i));
            }

            res.add(progressData);
        });
    }

}
