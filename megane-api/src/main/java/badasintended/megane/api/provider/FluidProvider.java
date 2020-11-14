package badasintended.megane.api.provider;

import java.util.function.BiFunction;
import java.util.function.Function;

import badasintended.megane.api.registry.BaseTooltipRegistry;
import net.minecraft.fluid.Fluid;
import org.jetbrains.annotations.Nullable;

public interface FluidProvider<T> extends BaseTooltipRegistry.Provider<T> {

    static <T> FluidProvider<T> of(Function<T, Integer> slotCount, BiFunction<T, Integer, Fluid> fluid, BiFunction<T, Integer, Double> stored, BiFunction<T, Integer, Double> max) {
        return of(t -> true, slotCount, fluid, stored, max);
    }

    static <T> FluidProvider<T> of(Function<T, Boolean> hasFluid, Function<T, Integer> slotCount, BiFunction<T, Integer, Fluid> fluid, BiFunction<T, Integer, Double> stored, BiFunction<T, Integer, Double> max) {
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
            public Fluid getFluid(T t, int slot) {
                return fluid.apply(t, slot);
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

    @Nullable
    Fluid getFluid(T t, int slot);

    double getStored(T t, int slot);

    double getMax(T t, int slot);

}
