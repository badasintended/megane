package lol.bai.megane.api.provider;

import lol.bai.megane.api.provider.base.SlotArrayProgressProvider;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * @see SlotArrayProgressProvider
 */
public abstract class ProgressProvider<T> extends AbstractProvider<T> {

    public boolean hasProgress() {
        return true;
    }

    public abstract int getInputSlotCount();

    public abstract int getOutputSlotCount();

    @NotNull
    public abstract ItemStack getInputStack(int slot);

    @NotNull
    public abstract ItemStack getOutputStack(int slot);

    /**
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
