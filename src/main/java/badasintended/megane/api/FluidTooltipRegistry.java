package badasintended.megane.api;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

@SuppressWarnings("unchecked")
public class FluidTooltipRegistry {

    private static final Map<Class<? extends BlockEntity>, Function<? extends BlockEntity, Integer>> SLOT_COUNT = new HashMap<>();
    private static final Map<Class<? extends BlockEntity>, BiFunction<? extends BlockEntity, Integer, ItemStack>> STACK = new HashMap<>();
    private static final Map<Class<? extends BlockEntity>, BiFunction<? extends BlockEntity, Integer, Float>> STORED = new HashMap<>();
    private static final Map<Class<? extends BlockEntity>, BiFunction<? extends BlockEntity, Integer, Float>> MAX = new HashMap<>();

    public static <T extends BlockEntity> void register(Class<T> clazz, Function<T, Integer> slotCount, BiFunction<T, Integer, ItemStack> stack, BiFunction<T, Integer, Float> stored, BiFunction<T, Integer, Float> max) {
        SLOT_COUNT.put(clazz, slotCount);
        STACK.put(clazz, stack);
        STORED.put(clazz, stored);
        MAX.put(clazz, max);
    }

    public static boolean valid(BlockEntity blockEntity) {
        return SLOT_COUNT.containsKey(blockEntity.getClass());
    }

    public static <T extends BlockEntity> int getSlotCount(T blockEntity) {
        return ((Function<T, Integer>) SLOT_COUNT.get(blockEntity.getClass())).apply(blockEntity);
    }

    public static <T extends BlockEntity> ItemStack getFluidStack(T blockEntity, int slot) {
        return ((BiFunction<T, Integer, ItemStack>) STACK.get(blockEntity.getClass())).apply(blockEntity, slot);
    }

    public static <T extends BlockEntity> float getStored(T blockEntity, int slot) {
        return ((BiFunction<T, Integer, Float>) STORED.get(blockEntity.getClass())).apply(blockEntity, slot);
    }

    public static <T extends BlockEntity> float getMax(T blockEntity, int slot) {
        return ((BiFunction<T, Integer, Float>) MAX.get(blockEntity.getClass())).apply(blockEntity, slot);
    }

}
