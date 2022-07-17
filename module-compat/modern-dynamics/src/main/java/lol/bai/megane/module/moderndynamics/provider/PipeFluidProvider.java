package lol.bai.megane.module.moderndynamics.provider;

import dev.technici4n.moderndynamics.network.fluid.FluidHost;
import dev.technici4n.moderndynamics.pipe.FluidPipeBlockEntity;
import lol.bai.megane.api.provider.FluidProvider;
import net.minecraft.fluid.Fluid;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("UnstableApiUsage")
public class PipeFluidProvider extends FluidProvider<FluidPipeBlockEntity> {

    FluidHost host;

    @Override
    protected void init() {
        host = ((FluidHost) getObject().getHosts()[0]);
    }

    @Override
    public int getSlotCount() {
        return 1;
    }

    @Override
    public @Nullable Fluid getFluid(int slot) {
        return host.getVariant().getFluid();
    }

    @Override
    public double getStored(int slot) {
        return droplets(host.getAmount());
    }

    @Override
    public double getMax(int slot) {
        return 1000;
    }

}
