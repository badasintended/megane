package badasintended.megane.runtime.component.block;

import java.util.function.Supplier;

import badasintended.megane.config.MeganeConfig;
import mcp.mobius.waila.api.IBlockAccessor;
import mcp.mobius.waila.api.IBlockComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.ITooltip;
import net.minecraft.util.registry.Registry;

public abstract class BlockComponent implements IBlockComponentProvider {

    private final Supplier<MeganeConfig.Base> baseConfig;

    protected BlockComponent(Supplier<MeganeConfig.Base> baseConfig) {
        this.baseConfig = baseConfig;
    }

    protected abstract void append(ITooltip tooltip, IBlockAccessor accessor);

    private void appendInternal(ITooltip tooltip, IBlockAccessor accessor) {
        if (baseConfig.get().isEnabled() && !baseConfig.get().getBlacklist().contains(Registry.BLOCK.getId(accessor.getBlock()))) {
            append(tooltip, accessor);
        }
    }

    @Override
    public void appendHead(ITooltip tooltip, IBlockAccessor accessor, IPluginConfig config) {
        appendInternal(tooltip, accessor);
    }

    @Override
    public void appendBody(ITooltip tooltip, IBlockAccessor accessor, IPluginConfig config) {
        appendInternal(tooltip, accessor);
    }

    @Override
    public void appendTail(ITooltip tooltip, IBlockAccessor accessor, IPluginConfig config) {
        appendInternal(tooltip, accessor);
    }

}
