package lol.bai.megane.runtime.provider.entity;

import java.util.function.Supplier;

import lol.bai.megane.runtime.config.MeganeConfig;
import mcp.mobius.waila.api.IEntityAccessor;
import mcp.mobius.waila.api.IEntityComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.ITooltip;
import net.minecraft.registry.Registries;

public abstract class EntityComponentProvider implements IEntityComponentProvider {

    private final Supplier<MeganeConfig.Base> baseConfig;

    protected EntityComponentProvider(Supplier<MeganeConfig.Base> baseConfig) {
        this.baseConfig = baseConfig;
    }

    abstract void append(ITooltip tooltip, IEntityAccessor accessor);

    private void appendInternal(ITooltip tooltip, IEntityAccessor accessor) {
        if (baseConfig.get().isEnabled() && !baseConfig.get().getBlacklist().contains(Registries.ENTITY_TYPE.getId(accessor.getEntity().getType()))) {
            append(tooltip, accessor);
        }
    }

    @Override
    public final void appendBody(ITooltip tooltip, IEntityAccessor accessor, IPluginConfig config) {
        appendInternal(tooltip, accessor);
    }

    @Override
    public final void appendHead(ITooltip tooltip, IEntityAccessor accessor, IPluginConfig config) {
        appendInternal(tooltip, accessor);
    }

    @Override
    public final void appendTail(ITooltip tooltip, IEntityAccessor accessor, IPluginConfig config) {
        appendInternal(tooltip, accessor);
    }

}
