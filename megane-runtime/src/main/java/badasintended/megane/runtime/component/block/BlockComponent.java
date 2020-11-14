package badasintended.megane.runtime.component.block;

import java.util.List;
import java.util.function.Supplier;

import badasintended.megane.config.MeganeConfig;
import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import net.minecraft.text.Text;
import net.minecraft.util.registry.Registry;

public abstract class BlockComponent implements IComponentProvider {

    private final Supplier<MeganeConfig.Base> baseConfig;

    protected BlockComponent(Supplier<MeganeConfig.Base> baseConfig) {
        this.baseConfig = baseConfig;
    }

    protected abstract void append(List<Text> tooltip, IDataAccessor accessor);

    private void appendInternal(List<Text> tooltip, IDataAccessor accessor) {
        if (baseConfig.get().isEnabled() && !baseConfig.get().getBlacklist().contains(Registry.BLOCK.getId(accessor.getBlock()))) {
            append(tooltip, accessor);
        }
    }

    @Override
    public final void appendBody(List<Text> tooltip, IDataAccessor accessor, IPluginConfig config) {
        appendInternal(tooltip, accessor);
    }

    @Override
    public final void appendHead(List<Text> tooltip, IDataAccessor accessor, IPluginConfig config) {
        appendInternal(tooltip, accessor);
    }

    @Override
    public final void appendTail(List<Text> tooltip, IDataAccessor accessor, IPluginConfig config) {
        appendInternal(tooltip, accessor);
    }

}
