package lol.bai.megane.module.create.provider;

import io.github.fabricators_of_create.porting_lib.util.FluidStack;
import lol.bai.megane.api.provider.FluidProvider;
import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.Nullable;

public abstract class FluidStackProvider<T> extends FluidProvider<T> {

    private FluidStack stack;

    abstract FluidStack getFluidStack(int slot);

    @Override
    public @Nullable Fluid getFluid(int slot) {
        stack = getFluidStack(slot);
        return stack.getFluid();
    }

    @Override
    public double getStored(int slot) {
        return droplets(stack.getAmount());
    }

    @Override
    public @Nullable NbtCompound getNbt(int slot) {
        return stack.getTag();
    }

}
