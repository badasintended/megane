package badasintended.megane.api.provider;

import badasintended.megane.api.registry.TooltipRegistry;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface ProgressProvider<T> extends TooltipRegistry.Provider<T> {

    static <T> ProgressProvider<T> of(Function<T, int[]> inputSlots, Function<T, int[]> outputSlots, BiFunction<T, Integer, ItemStack> stack, Function<T, Integer> percentage) {
        return of(t -> true, inputSlots, outputSlots, stack, percentage);
    }

    static <T> ProgressProvider<T> of(Function<T, Boolean> hasProgress, Function<T, int[]> inputSlots, Function<T, int[]> outputSlots, BiFunction<T, Integer, ItemStack> stack, Function<T, Integer> percentage) {
        return new ProgressProvider<T>() {
            @Override
            public boolean hasProgress(T t) {
                return hasProgress.apply(t);
            }

            @Override
            public int[] getInputSlots(T t) {
                return inputSlots.apply(t);
            }

            @Override
            public int[] getOutputSlots(T t) {
                return outputSlots.apply(t);
            }

            @Override
            public @NotNull ItemStack getStack(T t, int slot) {
                return stack.apply(t, slot);
            }

            @Override
            public int getPercentage(T t) {
                return percentage.apply(t);
            }
        };
    }

    boolean hasProgress(T t);

    int[] getInputSlots(T t);

    int[] getOutputSlots(T t);

    @NotNull
    ItemStack getStack(T t, int slot);

    int getPercentage(T t);

}
