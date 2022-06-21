package lol.bai.megane.api.provider;

import lol.bai.megane.api.provider.base.SlotArrayProgressProvider;
import lol.bai.megane.api.registry.CommonRegistrar;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Base class for progress provider.
 * <p>
 * Register implementations with {@link CommonRegistrar#addProgress}
 *
 * @param <T> type of {@link BlockEntity} this provider for.
 *
 * @see SlotArrayProgressProvider
 */
public abstract class ProgressProvider<T> extends AbstractProvider<T> {

    /**
     * Returns whether the object has a current progress going.
     */
    public boolean hasProgress() {
        return true;
    }

    /**
     * Returns the size of input slots.
     */
    public abstract int getInputSlotCount();

    /**
     * Returns the size of output slots.
     */
    public abstract int getOutputSlotCount();

    /**
     * Returns the stack for specified input slot.
     */
    @NotNull
    public abstract ItemStack getInputStack(int slot);

    /**
     * Returns the stack for specified output slot.
     */
    @NotNull
    public abstract ItemStack getOutputStack(int slot);

    /**
     * Returns the percentage of the progress.
     *
     * @see #currentPercentage(double, double)
     * @see #remainingPercentage(double, double)
     */
    public abstract int getPercentage();

    /**
     * Computes progress percentage from current time and total time needed.
     */
    public static int currentPercentage(double currentTime, double totalTime) {
        return (int) (currentTime / totalTime * 100);
    }

    /**
     * Computes progress percentage from remaining time and total time needed.
     */
    public static int remainingPercentage(double remainingTime, double totalTime) {
        return 100 - (int) (remainingTime / totalTime * 100);
    }

}
