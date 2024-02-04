package lol.bai.megane.module.ie.provider;

import blusunrize.immersiveengineering.api.multiblocks.blocks.logic.IMultiblockBE;
import blusunrize.immersiveengineering.api.multiblocks.blocks.logic.IMultiblockState;
import blusunrize.immersiveengineering.common.blocks.multiblocks.process.ProcessContext;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.FluidData;
import mcp.mobius.waila.api.data.ItemData;
import mcp.mobius.waila.api.forge.ForgeFluidData;
import net.minecraftforge.fluids.IFluidTank;

public class ProcessProvider<S extends IMultiblockState & ProcessContext<?>> implements IDataProvider<IMultiblockBE<S>> {

    @Override
    public void appendData(IDataWriter data, IServerAccessor<IMultiblockBE<S>> accessor, IPluginConfig config) {
        var state = accessor.getTarget().getHelper().getState();
        if (state == null) return;

        data.add(ItemData.class, res -> {
            var inventory = state.getInventory();
            if (inventory.getSlots() == 0) return;

            res.add(ItemData.of(config)
                .getter(inventory::getStackInSlot, inventory.getSlots()));
        });

        data.add(FluidData.class, res -> {
            var tanks = state.getInternalTanks();
            if (tanks.length == 0) return;

            var fluidData = ForgeFluidData.of(tanks.length);
            for (IFluidTank tank : tanks) {
                fluidData.add(tank.getFluid(), tank.getCapacity());
            }
            res.add(fluidData);
        });
    }

}
