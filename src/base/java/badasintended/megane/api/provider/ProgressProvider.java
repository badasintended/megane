package badasintended.megane.api.provider;

import java.util.function.Function;

import badasintended.megane.api.function.Functions;
import badasintended.megane.api.function.Functions.Obj2Bool;
import badasintended.megane.api.function.Functions.ObjInt2Obj;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface ProgressProvider<T> extends ContextAwareProvider {

    static <T> ProgressProvider<T> of(Function<T, int[]> inputSlots, Function<T, int[]> outputSlots, ObjInt2Obj<T, ItemStack> stack, Functions.Obj2Int<T> percentage) {
        return conditional(t -> true, inputSlots, outputSlots, stack, percentage);
    }

    static <T> ProgressProvider<T> conditional(Obj2Bool<T> hasProgress, Function<T, int[]> inputSlots, Function<T, int[]> outputSlots, ObjInt2Obj<T, ItemStack> stack, Functions.Obj2Int<T> percentage) {
        return new ProgressProvider<>() {
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

    default boolean hasProgress(T t) {
        return true;
    }

    int[] getInputSlots(T t);

    int[] getOutputSlots(T t);

    @NotNull
    ItemStack getStack(T t, int slot);

    int getPercentage(T t);

}
