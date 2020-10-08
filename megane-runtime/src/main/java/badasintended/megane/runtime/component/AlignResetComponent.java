package badasintended.megane.runtime.component;

import badasintended.megane.runtime.util.RuntimeUtils;
import mcp.mobius.waila.api.*;
import net.minecraft.text.Text;

import java.util.List;

public class AlignResetComponent {

    public static class Block implements IComponentProvider {

        @Override
        public void appendBody(List<Text> tooltip, IDataAccessor accessor, IPluginConfig config) {
            RuntimeUtils.align = 0;
        }

    }

    public static class Entity implements IEntityComponentProvider {

        @Override
        public void appendBody(List<Text> tooltip, IEntityAccessor accessor, IPluginConfig config) {
            RuntimeUtils.align = 0;
        }

    }

}
