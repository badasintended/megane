package lol.bai.megane.module.ie.provider;

import blusunrize.immersiveengineering.api.multiblocks.blocks.logic.IMultiblockBE;
import blusunrize.immersiveengineering.common.blocks.multiblocks.logic.CokeOvenLogic;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.ProgressData;

public class CokeOvenProvider implements IDataProvider<IMultiblockBE<CokeOvenLogic.State>> {

    @Override
    public void appendData(IDataWriter data, IServerAccessor<IMultiblockBE<CokeOvenLogic.State>> accessor, IPluginConfig config) {
        data.add(ProgressData.class, res -> {
            var state = accessor.getTarget().getHelper().getState();
            if (state == null) return;

            var inventory = state.getInventory();

            var processStep = (float) state.get(CokeOvenLogic.State.BURN_TIME);
            var processMax = (float) state.get(CokeOvenLogic.State.MAX_BURN_TIME);
            if (processStep == 0) return;

            res.add(ProgressData.ratio(processStep / processMax)
                .itemGetter(inventory::getStackInSlot)
                .input(0)
                .output(1));
        });
    }

}
