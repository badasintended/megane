package lol.bai.megane.runtime.provider.block;

import lol.bai.megane.runtime.component.ProgressComponent;
import mcp.mobius.waila.api.IBlockAccessor;
import mcp.mobius.waila.api.ITooltip;
import net.minecraft.nbt.NbtCompound;

import static lol.bai.megane.runtime.util.Keys.P_HAS;
import static lol.bai.megane.runtime.util.Keys.P_PERCENT;
import static lol.bai.megane.runtime.util.MeganeUtils.config;

public class ProgressComponentProvider extends BlockComponentProvider {

    public ProgressComponentProvider() {
        super(() -> config().progress);
    }

    @Override
    protected void append(ITooltip tooltip, IBlockAccessor accessor) {
        NbtCompound data = accessor.getServerData();
        if (data.getBoolean(P_HAS) && (data.getInt(P_PERCENT) > 0 || config().progress.isShowWhenZero())) {
            tooltip.addLine(new ProgressComponent(data));
        }
    }

}
