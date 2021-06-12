package badasintended.megane.runtime.component;

import java.util.List;

import badasintended.megane.runtime.util.RuntimeUtils;
import mcp.mobius.waila.api.IBlockAccessor;
import mcp.mobius.waila.api.IBlockComponentProvider;
import mcp.mobius.waila.api.IEntityAccessor;
import mcp.mobius.waila.api.IEntityComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import net.minecraft.text.Text;

public class AlignResetComponent {

    public static class Block implements IBlockComponentProvider {

        @Override
        public void appendHead(List<Text> tooltip, IBlockAccessor accessor, IPluginConfig config) {
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
