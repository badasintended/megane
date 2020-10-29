package badasintended.megane.api.provider;

import badasintended.megane.api.registry.TooltipRegistry;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface InventoryProvider<T> extends TooltipRegistry.Provider<T> {

    static <T> InventoryProvider<T> of(Function<T, Integer> size, BiFunction<T, Integer, ItemStack> stack) {
        return of(t -> true, size, stack);
    }

    static <T> InventoryProvider<T> of(Function<T, Boolean> hasInventory, Function<T, Integer> size, BiFunction<T, Integer, ItemStack> stack) {
        return new InventoryProvider<T>() {
            @Override
            public boolean hasInventory(T t) {
                return hasInventory.apply(t);
            }

            @Override
            public int size(T t) {
                return size.apply(t);
            }

            @Override
            public @NotNull ItemStack getStack(T t, int slot) {
                return stack.apply(t, slot);
            }
        };
    }

    boolean hasInventory(T t);

    int size(T t);

    @NotNull
    ItemStack getStack(T t, int slot);

}
