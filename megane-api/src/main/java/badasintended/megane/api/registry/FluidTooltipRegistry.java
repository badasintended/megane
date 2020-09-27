package badasintended.megane.api.registry;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.text.Text;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

@SuppressWarnings({"unchecked"})
public final class FluidTooltipRegistry {

    private static final Map<Class<? extends BlockEntity>, Provider<?>> ENTRIES = new HashMap<>();

    /**
     * Register BlockEntity class that has fluid storage on it.
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

        static <T extends BlockEntity> Provider<T> of(Function<T, Integer> slotCount, BiFunction<T, Integer, Text> name, BiFunction<T, Integer, Double> stored, BiFunction<T, Integer, Double> max) {
            return new Provider<T>() {
                @Override
                public int getSlotCount(T t) {
                    return slotCount.apply(t);
                }

                @Override
                public Text getFluidName(T t, int slot) {
                    return name.apply(t, slot);
                }

                @Override
                public double getStored(T t, int slot) {
                    return stored.apply(t, slot);
                }

                @Override
                public double getMax(T t, int slot) {
                    return max.apply(t, slot);
                }
            };
        }

        int getSlotCount(T t);

        Text getFluidName(T t, int slot);

        double getStored(T t, int slot);

        double getMax(T t, int slot);

    }

}
