package badasintended.megane.runtime.component;

import badasintended.megane.runtime.renderer.BarRenderer;
import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import net.minecraft.text.Text;

import java.util.List;

public class BarResetComponent implements IComponentProvider {

    public static final BarResetComponent INSTANCE = new BarResetComponent();

    @Override
    public void appendBody(List<Text> tooltip, IDataAccessor accessor, IPluginConfig config) {
        BarRenderer.INSTANCE.resetAlign();
    }

}
