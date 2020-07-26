package badasintended.megane.api.registry;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

@SuppressWarnings("unchecked")
public final class ProgressTooltipRegistry {

    private static final Map<Class<? extends BlockEntity>, Function<? extends BlockEntity, Integer>> INPUT_SLOT_COUNT = new HashMap<>();
    private static final Map<Class<? extends BlockEntity>, Function<? extends BlockEntity, Integer>> OUTPUT_SLOT_COUNT = new HashMap<>();

    private static final Map<Class<? extends BlockEntity>, BiFunction<? extends BlockEntity, Integer, ItemStack>> INPUT_STACK = new HashMap<>();
    private static final Map<Class<? extends BlockEntity>, BiFunction<? extends BlockEntity, Integer, ItemStack>> OUTPUT_STACK = new HashMap<>();

    private static final Map<Class<? extends BlockEntity>, Function<? extends BlockEntity, Integer>> PERCENTAGE = new HashMap<>();

    /**
     * Register BlockEntity Class that has some sort of processing in it, like furnace.
     *
     * @param clazz           highest class, any subclass will automatically get registered.
     * @param inputSlotCount  returns how many the input slot.
     * @param outputSlotCount returns how many the output slot.
     * @param inputStack      returns the stack for target input slot.
     * @param outputStack     returns the stack for target output slot.
     * @param percentage      returns the processing percentage.
     */
    @SuppressWarnings("unused")
    public static <T extends BlockEntity> void register(Class<T> clazz, Function<T, Integer> inputSlotCount, Function<T, Integer> outputSlotCount, BiFunction<T, Integer, ItemStack> inputStack, BiFunction<T, Integer, ItemStack> outputStack, Function<T, Integer> percentage) {
        INPUT_SLOT_COUNT.put(clazz, inputSlotCount);
        OUTPUT_SLOT_COUNT.put(clazz, outputSlotCount);
        INPUT_STACK.put(clazz, inputStack);
        OUTPUT_STACK.put(clazz, outputStack);
        PERCENTAGE.put(clazz, percentage);
    }

    @Nullable
    @ApiStatus.Internal
    public static <T extends BlockEntity> Class<T> getRegisteredClass(T blockEntity) {
        Class<?> clazz = blockEntity.getClass();
        boolean containsKey = INPUT_SLOT_COUNT.containsKey(clazz);

        if (!containsKey) do {
            clazz = clazz.getSuperclass();
            containsKey = INPUT_SLOT_COUNT.containsKey(clazz);
        } while (!containsKey && clazz != BlockEntity.class);

        if (containsKey) return (Class<T>) clazz;
        return null;
    }

    @ApiStatus.Internal
    public static <T extends BlockEntity, V extends BlockEntity> int getInputSlotCount(Class<T> clazz, V blockEntity) {
        return ((Function<T, Integer>) INPUT_SLOT_COUNT.get(clazz)).apply((T) blockEntity);
    }

    @ApiStatus.Internal
    public static <T extends BlockEntity, V extends BlockEntity> int getOutputSlotCount(Class<T> clazz, V blockEntity) {
        return ((Function<T, Integer>) OUTPUT_SLOT_COUNT.get(clazz)).apply((T) blockEntity);
    }

    @ApiStatus.Internal
    public static <T extends BlockEntity, V extends BlockEntity> ItemStack getInputStack(Class<T> clazz, V blockEntity, int slot) {
        return ((BiFunction<T, Integer, ItemStack>) INPUT_STACK.get(clazz)).apply((T) blockEntity, slot);
    }

    @ApiStatus.Internal
    public static <T extends BlockEntity, V extends BlockEntity> ItemStack getOutputStack(Class<T> clazz, V blockEntity, int slot) {
        return ((BiFunction<T, Integer, ItemStack>) OUTPUT_STACK.get(clazz)).apply((T) blockEntity, slot);
    }

    @ApiStatus.Internal
    public static <T extends BlockEntity, V extends BlockEntity> int getPercentage(Class<T> clazz, V blockEntity) {
        return ((Function<T, Integer>) PERCENTAGE.get(clazz)).apply((T) blockEntity);
    }

}
