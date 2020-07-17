package badasintended.megane.api;

import net.minecraft.block.entity.BlockEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@SuppressWarnings("unchecked")
public class EnergyTooltipRegistry {

    private static final Map<Class<? extends BlockEntity>, Function<? extends BlockEntity, String>> UNIT = new HashMap<>();
    private static final Map<Class<? extends BlockEntity>, Function<? extends BlockEntity, Float>> STORED = new HashMap<>();
    private static final Map<Class<? extends BlockEntity>, Function<? extends BlockEntity, Float>> MAX = new HashMap<>();

    public static <T extends BlockEntity> void register(Class<T> clazz, Function<T, String> unit, Function<T, Float> stored, Function<T, Float> max) {
        UNIT.put(clazz, unit);
        STORED.put(clazz, stored);
        MAX.put(clazz, max);
    }

    public static boolean valid(BlockEntity blockEntity) {
        return UNIT.containsKey(blockEntity.getClass());
    }

    public static <T extends BlockEntity> String getUnit(T blockEntity) {
        return ((Function<T, String>) UNIT.get(blockEntity.getClass())).apply(blockEntity);
    }

    public static <T extends BlockEntity> float getStored(T blockEntity) {
        return ((Function<T, Float>) STORED.get(blockEntity.getClass())).apply(blockEntity);
    }

    public static <T extends BlockEntity> float getMax(T blockEntity) {
        return ((Function<T, Float>) MAX.get(blockEntity.getClass())).apply(blockEntity);
    }

}
