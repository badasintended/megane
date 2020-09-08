package badasintended.megane.api.registry;

import net.minecraft.block.entity.BlockEntity;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@SuppressWarnings("unchecked")
public final class EnergyTooltipRegistry {

    private static final Map<Class<? extends BlockEntity>, Provider<?>> ENTRIES = new HashMap<>();

    /**
     * Register new BlockEntity class that has energy on it.
     *
     * @param clazz highest class, any subclass will automatically get registered.
     */
    @SuppressWarnings("unused")
    public static <T extends BlockEntity> void register(Class<T> clazz, Provider<T> provider) {
        ENTRIES.put(clazz, provider);
    }

    /**
     * @return the blockEntity class is registered energy provider.
     */
    @Nullable
    @ApiStatus.Internal
    public static <T extends BlockEntity> Provider<T> get(T blockEntity) {
        Class<?> clazz = blockEntity.getClass();
        boolean containsKey = ENTRIES.containsKey(clazz);

        if (!containsKey) do {
            clazz = clazz.getSuperclass();
            containsKey = ENTRIES.containsKey(clazz);
        } while (!containsKey && clazz != BlockEntity.class);

        if (containsKey) return (Provider<T>) ENTRIES.get(clazz);
        return null;
    }

    public interface Provider<T extends BlockEntity> {

        static <T extends BlockEntity> Provider<T> of(Function<T, Double> stored, Function<T, Double> max) {
            return new Provider<T>() {
                @Override
                public double getStored(T t) {
                    return stored.apply(t);
                }

                @Override
                public double getMax(T t) {
                    return max.apply(t);
                }
            };
        }

        double getStored(T t);

        double getMax(T t);

    }

}
