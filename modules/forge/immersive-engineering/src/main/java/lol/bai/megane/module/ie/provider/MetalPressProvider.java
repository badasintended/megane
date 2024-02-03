package lol.bai.megane.module.ie.provider;

import blusunrize.immersiveengineering.api.multiblocks.blocks.logic.IMultiblockBE;
import blusunrize.immersiveengineering.common.blocks.multiblocks.logic.MetalPressLogic;
import mcp.mobius.waila.api.IBlockAccessor;
import mcp.mobius.waila.api.IBlockComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.ITooltip;

import static lol.bai.megane.module.ie.MeganeImmersiveEngineering.CONFIG_SHOW_METAL_PRESS_MOLD;

public class MetalPressProvider implements IBlockComponentProvider {

    @Override
    public void appendBody(ITooltip tooltip, IBlockAccessor accessor, IPluginConfig config) {
        if (!config.getBoolean(CONFIG_SHOW_METAL_PRESS_MOLD)) return;

        IMultiblockBE<MetalPressLogic.State> be = accessor.getBlockEntity();
        if (be == null) return;

        var state = be.getHelper().getState();
        if (state == null) return;

        if (state.mold.isEmpty()) return;
        tooltip.addLine(state.mold.getHoverName());
    }

}
