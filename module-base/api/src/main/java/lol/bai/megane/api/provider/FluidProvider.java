package lol.bai.megane.api.provider;

import lol.bai.megane.api.registry.CommonRegistrar;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.Nullable;

/**
 * Base class for fluid provider.
 * <p>
 * Register implementations with {@link CommonRegistrar#addFluid}
 *
 * @param <T> type of {@link BlockEntity} this provider for.
 */
public abstract class FluidProvider<T> extends AbstractProvider<T> {

    /**
     * Returns whether the object has fluids.
     */
    public boolean hasFluids() {
        return true;
    }

    /**
     * Returns the fluid slot count of this object
     */
    public abstract int getSlotCount();

    /**
     * Returns the fluid of specified slot, or {@code null} if empty.
     */
    @Nullable
    public abstract Fluid getFluid(int slot);

    /**
     * Returns the fluid nbt of specified slot, or {@code null} if empty.
     */
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
