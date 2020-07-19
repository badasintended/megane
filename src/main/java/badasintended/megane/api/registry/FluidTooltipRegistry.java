package badasintended.megane.api.registry;

import net.minecraft.block.entity.BlockEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

@SuppressWarnings("unchecked")
public class FluidTooltipRegistry {

    private static final Map<Class<? extends BlockEntity>, Function<? extends BlockEntity, Integer>> SLOT_COUNT = new HashMap<>();
    private static final Map<Class<? extends BlockEntity>, BiFunction<? extends BlockEntity, Integer, String>> STACK = new HashMap<>();
    private static final Map<Class<? extends BlockEntity>, BiFunction<? extends BlockEntity, Integer, Integer>> COLOR = new HashMap<>();
    private static final Map<Class<? extends BlockEntity>, BiFunction<? extends BlockEntity, Integer, Double>> STORED = new HashMap<>();
    private static final Map<Class<? extends BlockEntity>, BiFunction<? extends BlockEntity, Integer, Double>> MAX = new HashMap<>();

    /**
     * Register BlockEntity class that has fluid storage on it.
     *
     * @param clazz     highest class, any subclass will automatically get registered.
     * @param slotCount returns how many fluid slot are.
     * @param name      returns fluid name.
     * @param stored    returns stored volume, in n Buckets.
     * @param max       returns tank capacity, in n Buckets.
     */
    public static <T extends BlockEntity> void register(Class<T> clazz, Function<T, Integer> slotCount, BiFunction<T, Integer, String> name, BiFunction<T, Integer, Double> stored, BiFunction<T, Integer, Double> max) {
        SLOT_COUNT.put(clazz, slotCount);
        STACK.put(clazz, name);
        STORED.put(clazz, stored);
        MAX.put(clazz, max);
    }

    public static <T extends BlockEntity> Class<T> getRegisteredClass(T blockEntity) {
        Class<?> clazz = blockEntity.getClass();
        boolean containsKey = SLOT_COUNT.containsKey(clazz);

        if (!containsKey) do {
            clazz = clazz.getSuperclass();
            containsKey = SLOT_COUNT.containsKey(clazz);
        } while (!containsKey && clazz != BlockEntity.class);

        if (containsKey) return (Class<T>) clazz;
        return null;
    }

    public static <T extends BlockEntity, V extends BlockEntity> int getSlotCount(Class<T> clazz, V blockEntity) {
        return ((Function<T, Integer>) SLOT_COUNT.get(clazz)).apply((T) blockEntity);
    }

    public static <T extends BlockEntity, V extends BlockEntity> String getFluidName(Class<T> clazz, V blockEntity, int slot) {
        return ((BiFunction<T, Integer, String>) STACK.get(clazz)).apply((T) blockEntity, slot);
    }

    public static <T extends BlockEntity, V extends BlockEntity> double getStored(Class<T> clazz, V blockEntity, int slot) {
        return ((BiFunction<T, Integer, Double>) STORED.get(clazz)).apply((T) blockEntity, slot);
    }

    public static <T extends BlockEntity, V extends BlockEntity> double getMax(Class<T> clazz, V blockEntity, int slot) {
        return ((BiFunction<T, Integer, Double>) MAX.get(clazz)).apply((T) blockEntity, slot);
    }

}
