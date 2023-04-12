package lol.bai.megane.runtime.provider.block;

import java.util.function.Supplier;

import lol.bai.megane.runtime.config.MeganeConfig;
import mcp.mobius.waila.api.IBlockAccessor;
import mcp.mobius.waila.api.IBlockComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.ITooltip;
import net.minecraft.registry.Registries;

public abstract class BlockComponentProvider implements IBlockComponentProvider {

    private final Supplier<MeganeConfig.Base> baseConfig;

    protected BlockComponentProvider(Supplier<MeganeConfig.Base> baseConfig) {
        this.baseConfig = baseConfig;
    }

    protected abstract void append(ITooltip tooltip, IBlockAccessor accessor);

    private void appendInternal(ITooltip tooltip, IBlockAccessor accessor) {
        if (baseConfig.get().isEnabled() && !baseConfig.get().getBlacklist().contains(Registries.BLOCK.getId(accessor.getBlock()))) {
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
