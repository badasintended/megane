package badasintended.megane.runtime.component.block;

import java.util.List;

import badasintended.megane.runtime.MeganeWaila;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.RenderableTextComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;

import static badasintended.megane.util.MeganeUtils.config;
import static badasintended.megane.util.MeganeUtils.key;

public class ProgressComponent extends BlockComponent {

    public ProgressComponent() {
        super(() -> config().progress);
    }

    @Override
    protected void append(List<Text> tooltip, IDataAccessor accessor) {
        CompoundTag data = accessor.getServerData();
        if (data.getBoolean(key("hasProgress")) && (data.getInt(key("percentage")) > 0 || config().progress.isShowWhenZero())) {
            tooltip.add(new RenderableTextComponent(MeganeWaila.PROGRESS, data));
        }
    }

}
