package badasintended.megane.runtime.component.block;

import java.util.List;

import badasintended.megane.runtime.Megane;
import mcp.mobius.waila.api.IBlockAccessor;
import mcp.mobius.waila.api.IDrawableText;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;

import static badasintended.megane.runtime.util.Keys.P_HAS;
import static badasintended.megane.runtime.util.Keys.P_PERCENT;
import static badasintended.megane.util.MeganeUtils.config;

public class ProgressComponent extends BlockComponent {

    public ProgressComponent() {
        super(() -> config().progress);
    }

    @Override
    protected void append(List<Text> tooltip, IBlockAccessor accessor) {
        NbtCompound data = accessor.getServerData();
        if (data.getBoolean(P_HAS) && (data.getInt(P_PERCENT) > 0 || config().progress.isShowWhenZero())) {
            tooltip.add(IDrawableText.of(Megane.PROGRESS, data));
        }
    }

}
