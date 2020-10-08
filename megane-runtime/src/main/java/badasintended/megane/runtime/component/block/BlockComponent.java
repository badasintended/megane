package badasintended.megane.runtime.component.block;

import badasintended.megane.config.MeganeConfig;
import mcp.mobius.waila.api.*;
import net.minecraft.text.Text;
import net.minecraft.util.registry.Registry;

import java.util.List;
import java.util.function.Supplier;

public abstract class BlockComponent implements IComponentProvider {

    private final Supplier<MeganeConfig.Base> baseConfig;

    protected BlockComponent(Supplier<MeganeConfig.Base> baseConfig) {
        this.baseConfig = baseConfig;
    }

    protected abstract void append(List<Text> tooltip, IDataAccessor accessor, IPluginConfig config);

    @Override
    public final void appendBody(List<Text> tooltip, IDataAccessor accessor, IPluginConfig config) {
        if (baseConfig.get().isEnabled() && !baseConfig.get().getBlacklist().contains(Registry.BLOCK.getId(accessor.getBlock()))) {
            append(tooltip, accessor, config);
        }
    }

}
