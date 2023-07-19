package lol.bai.megane.api.provider.base;

import lol.bai.megane.api.provider.ProgressProvider;
import lol.bai.megane.api.registry.CommonRegistrar;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Base class for slot array based progress provider.
 * <p>
 * Register implementations with {@link CommonRegistrar#addProgress}
 *
 * @param <T> type of {@link BlockEntity} this provider for.
 *
 * @deprecated use WTHIT API
 */
@Deprecated
public abstract class SlotArrayProgressProvider<T> extends ProgressProvider<T> {

    /**
     * Returns the input slot indexes.
     */
    protected abstract int[] getInputSlots();

    /**
     * Returns the output slot indexes.
     */
    protected abstract int[] getOutputSlots();

    /**
     * Returns the stack for the specified slot.
     */
    @NotNull
    protected abstract ItemStack getStack(int slot);

    @Override
    public int getInputSlotCount() {
        return getInputSlots().length;
    }

    @Override
    public int getOutputSlotCount() {
        return getOutputSlots().length;
    }

    @NotNull
    @Override
    public ItemStack getInputStack(int slot) {
        return getStack(getInputSlots()[slot]);
    }

    @NotNull
    @Override
    public ItemStack getOutputStack(int slot) {
        return getStack(getOutputSlots()[slot]);
    }

}
