package badasintended.megane.runtime.component.block;

import java.util.List;
import java.util.function.Supplier;

import badasintended.megane.config.MeganeConfig;
import mcp.mobius.waila.api.IBlockAccessor;
import mcp.mobius.waila.api.IBlockComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import net.minecraft.text.Text;
import net.minecraft.util.registry.Registry;

public abstract class BlockComponent implements IBlockComponentProvider {

    private final Supplier<MeganeConfig.Base> baseConfig;

    protected BlockComponent(Supplier<MeganeConfig.Base> baseConfig) {
        this.baseConfig = baseConfig;
    }

    protected abstract void append(List<Text> tooltip, IBlockAccessor accessor);

    private void appendInternal(List<Text> tooltip, IBlockAccessor accessor) {
        if (baseConfig.get().isEnabled() && !baseConfig.get().getBlacklist().contains(Registry.BLOCK.getId(accessor.getBlock()))) {
            append(tooltip, accessor);
        }
    }

    @Override
    public void appendBody(List<Text> tooltip, IBlockAccessor accessor, IPluginConfig config) {
        appendInternal(tooltip, accessor);
    }

    @Override
    public void appendHead(List<Text> tooltip, IBlockAccessor accessor, IPluginConfig config) {
        appendInternal(tooltip, accessor);
    }

    @Override
    public void appendTail(List<Text> tooltip, IBlockAccessor accessor, IPluginConfig config) {
        appendInternal(tooltip, accessor);
    }

}
