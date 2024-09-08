package lol.bai.megane.module.ie.provider;

import blusunrize.immersiveengineering.common.blocks.metal.MetalPressBlockEntity;
import mcp.mobius.waila.api.IBlockAccessor;
import mcp.mobius.waila.api.IBlockComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.ITooltip;

import static lol.bai.megane.module.ie.MeganeImmersiveEngineering.CONFIG_SHOW_METAL_PRESS_MOLD;

public class MetalPressProvider implements IBlockComponentProvider {

    @Override
    public void appendBody(ITooltip tooltip, IBlockAccessor accessor, IPluginConfig config) {
        if (!config.getBoolean(CONFIG_SHOW_METAL_PRESS_MOLD)) return;

        MetalPressBlockEntity be = accessor.getBlockEntity();
        if (be == null) return;

        var master = be.master();
        if (master == null) return;

        if (master.mold.isEmpty()) return;
        tooltip.addLine(master.mold.getHoverName());
    }

}
