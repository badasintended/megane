package badasintended.megane.runtime.component;

import badasintended.megane.runtime.MeganeWaila;
import badasintended.megane.util.MeganeUtils;
import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.RenderableTextComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;

import java.util.List;

public class ProgressComponent implements IComponentProvider {

    public static final ProgressComponent INSTANCE = new ProgressComponent();

    @Override
    public void appendBody(List<Text> tooltip, IDataAccessor accessor, IPluginConfig config) {
        if (!MeganeUtils.config().progress.isEnabled()) return;

        CompoundTag data = accessor.getServerData();

        if (data.getBoolean(MeganeUtils.key("hasProgress")) && data.getInt(MeganeUtils.key("percentage")) > 0) {
            tooltip.add(new RenderableTextComponent(MeganeWaila.PROGRESS, data));
        }
    }

}
