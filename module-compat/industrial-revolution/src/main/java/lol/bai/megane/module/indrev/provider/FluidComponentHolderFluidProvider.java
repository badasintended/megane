package lol.bai.megane.module.indrev.provider;

import java.util.function.Function;

import lol.bai.megane.api.provider.FluidProvider;
import me.steven.indrev.components.FluidComponent;
import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("UnstableApiUsage")
public class FluidComponentHolderFluidProvider<T> extends FluidProvider<T> {

    private final Function<T, FluidComponent> fluidComponentGetter;

    private FluidComponent fluidComponent;

    public FluidComponentHolderFluidProvider(Function<T, @Nullable FluidComponent> fluidComponentGetter) {
        this.fluidComponentGetter = fluidComponentGetter;
    }

    @Override
    protected void init() {
        this.fluidComponent = fluidComponentGetter.apply(getObject());
    }

    @Override
    public boolean hasFluids() {
        return fluidComponent != null;
    }

    @Override
    public int getSlotCount() {
        return fluidComponent.getTankCount();
    }

    @Override
    public @Nullable Fluid getFluid(int slot) {
        return fluidComponent.get(slot).variant.getFluid();
    }

    @Override
    public @Nullable NbtCompound getNbt(int slot) {
        return fluidComponent.get(slot).variant.getNbt();
    }

    @Override
    public double getStored(int slot) {
        return droplets(fluidComponent.get(slot).amount);
    }

    @Override
    public double getMax(int slot) {
        return droplets(fluidComponent.getTankCapacity(slot));
    }

}
