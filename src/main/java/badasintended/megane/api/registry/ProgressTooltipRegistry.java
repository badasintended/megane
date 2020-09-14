package badasintended.megane.api.registry;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

@SuppressWarnings({"unchecked", "unused"})
public final class ProgressTooltipRegistry {

    private static final Map<Class<? extends BlockEntity>, Provider<?>> ENTRIES = new HashMap<>();

    /**
     * Register BlockEntity Class that has some sort of processing in it, like furnace.
     *
     * @param clazz highest class, any subclass will automatically get registered.
     */
    public static <T extends BlockEntity> void register(Class<T> clazz, Provider<T> provider) {
        ENTRIES.put(clazz, provider);
    }

    @Nullable
    @ApiStatus.Internal
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

        static <T extends BlockEntity> Provider<T> of(Function<T, int[]> inputSlots, Function<T, int[]> outputSlots, BiFunction<T, Integer, ItemStack> stack, Function<T, Integer> percentage) {
            return new Provider<T>() {
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

        int[] getInputSlots(T t);

        int[] getOutputSlots(T t);

        @NotNull
        ItemStack getStack(T t, int slot);

        int getPercentage(T t);

    }

}
