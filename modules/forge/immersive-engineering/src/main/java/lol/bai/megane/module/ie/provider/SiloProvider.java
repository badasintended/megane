package lol.bai.megane.module.ie.provider;

import blusunrize.immersiveengineering.api.multiblocks.blocks.logic.IMultiblockBE;
import blusunrize.immersiveengineering.common.blocks.multiblocks.logic.SiloLogic;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.ItemData;
import net.minecraftforge.items.ItemHandlerHelper;

public class SiloProvider implements IDataProvider<IMultiblockBE<SiloLogic.State>> {

    @Override
    public void appendData(IDataWriter data, IServerAccessor<IMultiblockBE<SiloLogic.State>> accessor, IPluginConfig config) {
        data.add(ItemData.class, res -> {
            var state = accessor.getTarget().getHelper().getState();
            if (state == null) return;

            res.add(ItemData.of(config)
                .add(ItemHandlerHelper.copyStackWithSize(state.identStack, state.storageAmount)));
        });
    }

}
