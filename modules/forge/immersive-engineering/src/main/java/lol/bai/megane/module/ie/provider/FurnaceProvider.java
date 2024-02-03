package lol.bai.megane.module.ie.provider;

import java.util.function.Function;

import blusunrize.immersiveengineering.api.multiblocks.blocks.logic.IMultiblockBE;
import blusunrize.immersiveengineering.api.multiblocks.blocks.logic.IMultiblockState;
import blusunrize.immersiveengineering.common.blocks.multiblocks.logic.FurnaceHandler;
import lol.bai.megane.module.ie.mixin.AccessFurnaceHandler;
import lol.bai.megane.module.ie.mixin.AccessFurnaceHandlerStateView;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.ItemData;
import mcp.mobius.waila.api.data.ProgressData;
import net.minecraft.world.inventory.ContainerData;

public class FurnaceProvider<S extends IMultiblockState & FurnaceHandler.IFurnaceEnvironment<?>> implements IDataProvider<IMultiblockBE<S>> {

    private final Function<S, ContainerData> containerDataFunction;

    public FurnaceProvider(Function<S, ContainerData> containerDataFunction) {
        this.containerDataFunction = containerDataFunction;
    }

    @Override
    public void appendData(IDataWriter data, IServerAccessor<IMultiblockBE<S>> accessor, IPluginConfig config) {
        var state = accessor.getTarget().getHelper().getState();
        if (state == null) return;

        var inventory = state.getInventory();
        if (inventory == null) return;

        data.add(ItemData.class, res ->
            res.add(ItemData.of(config).getter(inventory::getStackInSlot, inventory.getSlots())));

        data.add(ProgressData.class, res -> {
            var stateView = (FurnaceHandler<?>.StateView & AccessFurnaceHandlerStateView) containerDataFunction.apply(state);
            var processStep = (float) FurnaceHandler.StateView.getProcess(stateView);
            var processMax = (float) FurnaceHandler.StateView.getMaxProcess(stateView);
            if (processStep == 0) return;

            var ratio = processStep / processMax;
            var progressData = ProgressData.ratio(ratio)
                .itemGetter(inventory::getStackInSlot);

            var access = (AccessFurnaceHandler) stateView.megane_FurnaceHandler_this();
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
