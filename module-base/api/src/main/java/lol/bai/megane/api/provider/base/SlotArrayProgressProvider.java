package lol.bai.megane.api.provider.base;

import lol.bai.megane.api.provider.ProgressProvider;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public abstract class SlotArrayProgressProvider<T> extends ProgressProvider<T> {

    protected abstract int[] getInputSlots();

    protected abstract int[] getOutputSlots();

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
