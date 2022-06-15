package lol.bai.megane.module.kibe.provider;

import java.util.function.Function;

import lol.bai.megane.api.provider.FluidProvider;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.fluid.Fluid;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("UnstableApiUsage")
public class TankFluidProvider<T> extends FluidProvider<T> {

    private final Function<T, SingleVariantStorage<FluidVariant>> tankGetter;
    private SingleVariantStorage<FluidVariant> tank;

    public TankFluidProvider(Function<T, SingleVariantStorage<FluidVariant>> tankGetter) {
        this.tankGetter = tankGetter;
    }

    @Override
    protected void init() {
        this.tank = tankGetter.apply(getObject());
    }

    @Override
    public int getSlotCount() {
        return 1;
    }

    @Override
    public @Nullable Fluid getFluid(int slot) {
        return tank.variant.getFluid();
    }

    @Override
    public double getStored(int slot) {
        return droplets(tank.getAmount());
    }

    @Override
    public double getMax(int slot) {
        return droplets(tank.getCapacity());
    }

}
