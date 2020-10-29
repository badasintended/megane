package badasintended.megane.api.provider;

import badasintended.megane.api.registry.TooltipRegistry;
import net.minecraft.text.Text;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface FluidProvider<T> extends TooltipRegistry.Provider<T> {

    static <T> FluidProvider<T> of(Function<T, Integer> slotCount, BiFunction<T, Integer, Text> name, BiFunction<T, Integer, Double> stored, BiFunction<T, Integer, Double> max) {
        return of(t -> true, slotCount, name, stored, max);
    }

    static <T> FluidProvider<T> of(Function<T, Boolean> hasFluid, Function<T, Integer> slotCount, BiFunction<T, Integer, Text> name, BiFunction<T, Integer, Double> stored, BiFunction<T, Integer, Double> max) {
        return new FluidProvider<T>() {
            @Override
            public boolean hasFluid(T t) {
                return hasFluid.apply(t);
            }

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

    boolean hasFluid(T t);

    int getSlotCount(T t);

    Text getFluidName(T t, int slot);

    double getStored(T t, int slot);

    double getMax(T t, int slot);

}
