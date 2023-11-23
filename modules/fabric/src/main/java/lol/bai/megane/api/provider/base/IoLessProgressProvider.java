package lol.bai.megane.api.provider.base;

import lol.bai.megane.api.provider.ProgressProvider;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * @deprecated use WTHIT API
 */
@Deprecated
public abstract class IoLessProgressProvider<T> extends ProgressProvider<T> {

    @Override
    public int getInputSlotCount() {
        return 0;
    }

    @Override
    public int getOutputSlotCount() {
        return 0;
    }

    @Override
    public @NotNull ItemStack getInputStack(int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull ItemStack getOutputStack(int slot) {
        return ItemStack.EMPTY;
    }

}
