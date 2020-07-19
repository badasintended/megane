package badasintended.megane.api.registry;

import net.minecraft.block.entity.BlockEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@SuppressWarnings("unchecked")
public class EnergyTooltipRegistry {

    private static final Map<Class<? extends BlockEntity>, String> UNIT = new HashMap<>();
    private static final Map<Class<? extends BlockEntity>, Function<? extends BlockEntity, Double>> STORED = new HashMap<>();
    private static final Map<Class<? extends BlockEntity>, Function<? extends BlockEntity, Double>> MAX = new HashMap<>();

    /**
     * Register new BlockEntity class that has energy on it.
     *
     * @param clazz  highest class, any subclass will automatically get registered.
     * @param unit   function to return the energy unit
     * @param stored function to return stored energy
     * @param max    function to return tank capacity
     */
    public static <T extends BlockEntity> void register(Class<T> clazz, String unit, Function<T, Double> stored, Function<T, Double> max) {
        UNIT.put(clazz, unit);
        STORED.put(clazz, stored);
        MAX.put(clazz, max);
    }

    /**
     * @return the blockEntity class is registered energy provider.
     */
    public static <T extends BlockEntity> Class<T> getRegisteredClass(T blockEntity) {
        Class<?> clazz = blockEntity.getClass();
        boolean containsKey = UNIT.containsKey(clazz);

        if (!containsKey) do {
            clazz = clazz.getSuperclass();
            containsKey = UNIT.containsKey(clazz);
        } while (!containsKey && clazz != BlockEntity.class);

        if (containsKey) return (Class<T>) clazz;
        return null;
    }

    /**
     * @return energy unit.
     */
    public static <T extends BlockEntity> String getUnit(Class<T> clazz) {
        return UNIT.get(clazz);
    }

    /**
     * @return stored energy.
     */
    public static <T extends BlockEntity, V extends BlockEntity> double getStored(Class<T> clazz, V blockEntity) {
        return ((Function<T, Double>) STORED.get(clazz)).apply((T) blockEntity);
    }

    /**
     * @return energy capacity.
     */
    public static <T extends BlockEntity, V extends BlockEntity> double getMax(Class<T> clazz, V blockEntity) {
        return ((Function<T, Double>) MAX.get(clazz)).apply((T) blockEntity);
    }

}
