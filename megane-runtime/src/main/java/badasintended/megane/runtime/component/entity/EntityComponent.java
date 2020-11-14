package badasintended.megane.runtime.component.entity;

import java.util.List;
import java.util.function.Supplier;

import badasintended.megane.config.MeganeConfig;
import mcp.mobius.waila.api.IEntityAccessor;
import mcp.mobius.waila.api.IEntityComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import net.minecraft.text.Text;
import net.minecraft.util.registry.Registry;

public abstract class EntityComponent implements IEntityComponentProvider {

    private final Supplier<MeganeConfig.Base> baseConfig;

    protected EntityComponent(Supplier<MeganeConfig.Base> baseConfig) {
        this.baseConfig = baseConfig;
    }

    abstract void append(List<Text> tooltip, IEntityAccessor accessor);

    private void appendInternal(List<Text> tooltip, IEntityAccessor accessor) {
        if (baseConfig.get().isEnabled() && !baseConfig.get().getBlacklist().contains(Registry.ENTITY_TYPE.getId(accessor.getEntity().getType()))) {
            append(tooltip, accessor);
        }
    }

    @Override
    public final void appendBody(List<Text> tooltip, IEntityAccessor accessor, IPluginConfig config) {
        appendInternal(tooltip, accessor);
    }

    @Override
    public final void appendHead(List<Text> tooltip, IEntityAccessor accessor, IPluginConfig config) {
        appendInternal(tooltip, accessor);
    }

    @Override
    public final void appendTail(List<Text> tooltip, IEntityAccessor accessor, IPluginConfig config) {
        appendInternal(tooltip, accessor);
    }

}
