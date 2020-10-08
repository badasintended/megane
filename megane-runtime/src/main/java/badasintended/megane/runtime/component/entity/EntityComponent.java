package badasintended.megane.runtime.component.entity;

import badasintended.megane.config.MeganeConfig;
import mcp.mobius.waila.api.*;
import net.minecraft.text.Text;
import net.minecraft.util.registry.Registry;

import java.util.List;
import java.util.function.Supplier;

public abstract class EntityComponent implements IEntityComponentProvider {

    private final Supplier<MeganeConfig.Base> baseConfig;

    protected EntityComponent(Supplier<MeganeConfig.Base> baseConfig) {
        this.baseConfig = baseConfig;
    }

    protected void body(List<Text> tooltip, IEntityAccessor accessor) {
    }

    @Override
    public final void appendBody(List<Text> tooltip, IEntityAccessor accessor, IPluginConfig config) {
        if (baseConfig.get().isEnabled() && !baseConfig.get().getBlacklist().contains(Registry.ENTITY_TYPE.getId(accessor.getEntity().getType()))) {
            body(tooltip, accessor);
        }
    }

}
