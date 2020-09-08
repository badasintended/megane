package badasintended.megane.tooltip.component;

import badasintended.megane.Megane;
import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.RenderableTextComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;

import java.util.List;

import static badasintended.megane.MeganeUtils.CONFIG;
import static badasintended.megane.MeganeUtils.key;

public class ProgressComponent implements IComponentProvider {

    public static final ProgressComponent INSTANCE = new ProgressComponent();

    @Override
    public void appendBody(List<Text> tooltip, IDataAccessor accessor, IPluginConfig config) {
        if (!CONFIG.get().progress.isEnabled()) return;

        CompoundTag data = accessor.getServerData();

        if (data.getBoolean(key("hasProgress")) && data.getInt(key("percentage")) > 0) {
            tooltip.add(new RenderableTextComponent(Megane.PROGRESS, data));
        }
    }

}
