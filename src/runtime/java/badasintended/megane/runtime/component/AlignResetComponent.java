package badasintended.megane.runtime.component;

import java.util.List;

import badasintended.megane.runtime.util.RuntimeUtils;
import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IEntityAccessor;
import mcp.mobius.waila.api.IEntityComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import net.minecraft.text.Text;

public class AlignResetComponent {

    public static class Block implements IComponentProvider {

        @Override
        public void appendHead(List<Text> tooltip, IDataAccessor accessor, IPluginConfig config) {
            RuntimeUtils.align = 0;
        }

    }

    public static class Entity implements IEntityComponentProvider {

        @Override
        public void appendHead(List<Text> tooltip, IEntityAccessor accessor, IPluginConfig config) {
            RuntimeUtils.align = 0;
        }

    }

}
