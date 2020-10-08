package badasintended.megane.api.provider;

import badasintended.megane.api.registry.BaseTooltipRegistry;

import java.util.function.Function;

public interface EnergyProvider<T> extends BaseTooltipRegistry.Provider<T> {

    static <T> EnergyProvider<T> of(Function<T, Double> stored, Function<T, Double> max) {
        return of(t -> true, stored, max);
    }

    static <T> EnergyProvider<T> of(Function<T, Boolean> hasEnergy, Function<T, Double> stored, Function<T, Double> max) {
        return new EnergyProvider<T>() {
            @Override
            public boolean hasEnergy(T t) {
                return hasEnergy.apply(t);
            }

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

    boolean hasEnergy(T t);

    double getStored(T t);

    double getMax(T t);

}
