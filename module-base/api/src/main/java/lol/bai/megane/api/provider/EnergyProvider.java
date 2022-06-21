package lol.bai.megane.api.provider;

import lol.bai.megane.api.registry.CommonRegistrar;
import net.minecraft.block.entity.BlockEntity;

/**
 * Base class for energy provider.
 * <p>
 * Register implementations with {@link CommonRegistrar#addEnergy}
 *
 * @param <T> type of {@link BlockEntity} this provider for.
 */
public abstract class EnergyProvider<T> extends AbstractProvider<T> {

    /**
     * Returns whether the object has an energy.
     */
    public boolean hasEnergy() {
        return true;
    }

    /**
     * Returns how much energy is currently stored.
     * <p>
     * Return {@code -1} to mark this as unlimited energy.
     */
    public abstract long getStored();

    /**
     * Returns how much the capacity of the storage.
     * <p>
     * Return {@code -1} to mark this as unlimited capacity.
     */
    public abstract long getMax();

}
