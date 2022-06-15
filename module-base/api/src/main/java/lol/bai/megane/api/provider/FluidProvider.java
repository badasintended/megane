package lol.bai.megane.api.provider;

import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.Nullable;

public abstract class FluidProvider<T> extends AbstractProvider<T> {

    public boolean hasFluids() {
        return true;
    }

    public abstract int getSlotCount();

    @Nullable
    public abstract Fluid getFluid(int slot);

    @Nullable
    public NbtCompound getNbt(int slot) {
        return null;
    }

    /**
     * @return stored fluid in slot, in millibuckets.
     *
     * @see #droplets(double)
     */
    public abstract double getStored(int slot);

    /**
     * @return the slot capacity, in millibuckets.
     *
     * @see #droplets(double)
     */
    public abstract double getMax(int slot);

    /**
     * Converts droplet-based unit to millibuckets.
     */
    public static double droplets(double droplets) {
        return droplets / 81.0;
    }

}
