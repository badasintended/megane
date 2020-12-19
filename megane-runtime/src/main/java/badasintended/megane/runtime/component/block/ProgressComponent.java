package badasintended.megane.runtime.component.block;

import java.util.List;

import badasintended.megane.runtime.MeganeWaila;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.RenderableTextComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;

import static badasintended.megane.runtime.util.Keys.P_HAS;
import static badasintended.megane.runtime.util.Keys.P_PERCENT;
import static badasintended.megane.util.MeganeUtils.config;

public class ProgressComponent extends BlockComponent {

    public ProgressComponent() {
        super(() -> config().progress);
    }

    @Override
    protected void append(List<Text> tooltip, IDataAccessor accessor) {
        CompoundTag data = accessor.getServerData();
        if (data.getBoolean(P_HAS) && (data.getInt(P_PERCENT) > 0 || config().progress.isShowWhenZero())) {
            tooltip.add(new RenderableTextComponent(MeganeWaila.PROGRESS, data));
        }
    }

}
