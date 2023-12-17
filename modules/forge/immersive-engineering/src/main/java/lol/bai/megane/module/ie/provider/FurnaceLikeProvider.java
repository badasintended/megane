package lol.bai.megane.module.ie.provider;

import blusunrize.immersiveengineering.common.blocks.stone.FurnaceLikeBlockEntity;
import lol.bai.megane.module.ie.mixin.AccessFurnaceLikeBlockEntity;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.ProgressData;

public class FurnaceLikeProvider implements IDataProvider<FurnaceLikeBlockEntity<?, ?>> {

    @Override
    public void appendData(IDataWriter data, IServerAccessor<FurnaceLikeBlockEntity<?, ?>> accessor, IPluginConfig config) {
        data.add(ProgressData.class, res -> {
            var furnace = accessor.getTarget().master();
            if (furnace == null) return;
            if (!furnace.getIsActive()) return;

            var inventory = furnace.getInventory();
            if (inventory == null) return;

            var processStep = (float) furnace.getCurrentProcessesStep()[0];
            var processMax = (float) furnace.getCurrentProcessesMax()[0];
            if (processStep == 0) return;

            var ratio = processStep / processMax;
            var progressData = ProgressData.ratio(ratio)
                .itemGetter(inventory::get);

            var access = (AccessFurnaceLikeBlockEntity) furnace;
            access.megane_inputs().forEach(it -> progressData.input(it.megane_slotIndex()));
            progressData.input(access.megane_fuelSlot());
            access.megane_outputs().forEach(it -> progressData.output(it.megane_slotIndex()));

            res.add(progressData);
        });
    }

    public interface Slot {

        int megane_slotIndex();

    }

}
