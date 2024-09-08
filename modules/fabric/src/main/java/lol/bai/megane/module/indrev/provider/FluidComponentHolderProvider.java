package lol.bai.megane.module.indrev.provider;

import java.util.function.Function;

import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.FluidData;
import mcp.mobius.waila.api.fabric.FabricFluidData;
import me.steven.indrev.components.FluidComponent;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("UnstableApiUsage")
public class FluidComponentHolderProvider<T> implements IDataProvider<T> {

    private final Function<T, @Nullable FluidComponent> getter;

    public FluidComponentHolderProvider(Function<T, @Nullable FluidComponent> getter) {
        this.getter = getter;
    }

    @Override
    public void appendData(IDataWriter data, IServerAccessor<T> accessor, IPluginConfig config) {
        data.add(FluidData.class, res -> {
            var component = getter.apply(accessor.getTarget());
            if (component == null) return;

            var fluidData = FabricFluidData.of(component.getTankCount());
            for (var part : component.parts) {
                fluidData.add(part.getResource(), part.getAmount(), part.getCapacity());
            }

            res.add(fluidData);
        });
    }

}
