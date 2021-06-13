package badasintended.megane.api.provider;

import badasintended.megane.api.function.Functions.Obj2Bool;
import badasintended.megane.api.function.Functions.Obj2Int;
import badasintended.megane.api.function.Functions.ObjInt2Obj;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface InventoryProvider<T> extends ContextAwareProvider {

    static <T> InventoryProvider<T> of(Obj2Int<T> size, ObjInt2Obj<T, ItemStack> stack) {
        return conditional(t -> true, size, stack);
    }

    static <T> InventoryProvider<T> conditional(Obj2Bool<T> hasInventory, Obj2Int<T> size, ObjInt2Obj<T, ItemStack> stack) {
        return new InventoryProvider<>() {
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

    default boolean hasInventory(T t) {
        return true;
    }

    int size(T t);

    @NotNull
    ItemStack getStack(T t, int slot);

}
