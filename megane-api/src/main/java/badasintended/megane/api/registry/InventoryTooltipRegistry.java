package badasintended.megane.api.registry;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public final class InventoryTooltipRegistry {

    private static final Map<Class<? extends BlockEntity>, Provider<?>> ENTRIES = new HashMap<>();

    public static <T extends BlockEntity> void register(Class<T> clazz, Provider<T> provider) {
        ENTRIES.put(clazz, provider);
    }

    @Nullable
    @ApiStatus.Internal
    @SuppressWarnings({"unchecked"})
    public static <T extends BlockEntity> Provider<T> get(T blockEntity) {
        Class<?> clazz = blockEntity.getClass();
        boolean containsKey = ENTRIES.containsKey(clazz);

        if (!containsKey) do {
            clazz = clazz.getSuperclass();
            containsKey = ENTRIES.containsKey(clazz);
        } while (!containsKey && clazz != BlockEntity.class);

        if (containsKey) {
            Provider<T> provider = (Provider<T>) ENTRIES.get(clazz);
            ENTRIES.putIfAbsent(blockEntity.getClass(), provider);
            return provider;
        }
        return null;
    }

    public interface Provider<T extends BlockEntity> {

        static <T extends BlockEntity> Provider<T> of(Function<T, Integer> size, BiFunction<T, Integer, ItemStack> stack) {
            return of(t -> true, size, stack);
        }

        static <T extends BlockEntity> Provider<T> of(Function<T, Boolean> hasInventory, Function<T, Integer> size, BiFunction<T, Integer, ItemStack> stack) {
            return new Provider<T>() {
                @Override
                public boolean hasInventory(T t) {
                    return hasInventory.apply(t);
                }

                @Override
                public int size(T t) {
                    return size.apply(t);
                }

                @Override
                public ItemStack getStack(T t, int slot) {
                    return stack.apply(t, slot);
                }
            };
        }

        boolean hasInventory(T t);

        int size(T t);

        ItemStack getStack(T t, int slot);

    }

}
