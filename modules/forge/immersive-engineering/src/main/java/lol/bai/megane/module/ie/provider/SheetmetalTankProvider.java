package lol.bai.megane.module.ie.provider;

import blusunrize.immersiveengineering.api.multiblocks.blocks.logic.IMultiblockBE;
import blusunrize.immersiveengineering.common.blocks.multiblocks.logic.SheetmetalTankLogic;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.FluidData;
import mcp.mobius.waila.api.forge.ForgeFluidData;

public class SheetmetalTankProvider implements IDataProvider<IMultiblockBE<SheetmetalTankLogic.State>> {

    @Override
    public void appendData(IDataWriter data, IServerAccessor<IMultiblockBE<SheetmetalTankLogic.State>> accessor, IPluginConfig config) {
        data.add(FluidData.class, res -> {
            var state = accessor.getTarget().getHelper().getState();
            if (state == null) return;

            res.add(ForgeFluidData.of(1)
                .add(state.tank.getFluid(), state.tank.getCapacity()));
        });
    }

}
