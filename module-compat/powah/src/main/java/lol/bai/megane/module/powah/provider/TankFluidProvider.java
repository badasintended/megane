package lol.bai.megane.module.powah.provider;

import java.util.function.Function;

import dev.architectury.fluid.FluidStack;
import lol.bai.megane.api.provider.FluidProvider;
import net.minecraft.fluid.Fluid;
import org.jetbrains.annotations.Nullable;
import owmii.powah.lib.logistics.fluid.Tank;

public class TankFluidProvider<T> extends FluidProvider<T> {

    final Function<T, Tank> getter;

    Tank tank;
    FluidStack stack;

    public TankFluidProvider(Function<T, Tank> getter) {
        this.getter = getter;
    }

    @Override
    protected void init() {
        this.tank = getter.apply(getObject());
    }

    @Override
    public boolean hasFluids() {
        return tank != null;
    }

    @Override
    public int getSlotCount() {
        return tank.getTanks();
    }

    @Override
    public @Nullable Fluid getFluid(int slot) {
        this.stack = tank.getFluidInTank(slot);
        return stack.getFluid();
    }

    @Override
    public double getStored(int slot) {
        return droplets(stack.getAmount());
    }

    @Override
    public double getMax(int slot) {
        return droplets(tank.getTankCapacity(slot));
    }

}
