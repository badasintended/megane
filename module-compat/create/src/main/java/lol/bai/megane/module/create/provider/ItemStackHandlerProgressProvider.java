package lol.bai.megane.module.create.provider;

import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler;
import lol.bai.megane.api.provider.ProgressProvider;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ItemStackHandlerProgressProvider<T> extends ProgressProvider<T> {

    @Nullable
    private ItemStackHandler input;

    @Nullable
    private ItemStackHandler output;

    @Override
    protected void init() {
        super.init();
        input = getInputStackHandler();
        output = getOutputStackHandler();
    }

    @Nullable
    abstract ItemStackHandler getInputStackHandler();

    @Nullable
    abstract ItemStackHandler getOutputStackHandler();

    @Override
    public int getInputSlotCount() {
        return input == null ? 0 : input.getSlots();
    }

    @Override
    public int getOutputSlotCount() {
        return output == null ? 0 : output.getSlots();
    }

    @Override
    public @NotNull ItemStack getInputStack(int slot) {
        return input == null ? ItemStack.EMPTY : input.getStackInSlot(slot);
    }

    @Override
    public @NotNull ItemStack getOutputStack(int slot) {
        return output == null ? ItemStack.EMPTY : output.getStackInSlot(slot);
    }

}
