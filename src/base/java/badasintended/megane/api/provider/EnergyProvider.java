package badasintended.megane.api.provider;

import badasintended.megane.api.function.Functions.Obj2Bool;
import badasintended.megane.api.function.Functions.Obj2Double;

/**
 * for things that
 *
 * @param <T> something that contains energy.
 */
public interface EnergyProvider<T> extends ContextAwareProvider {

    static <T> EnergyProvider<T> of(Obj2Double<T> stored, Obj2Double<T> max) {
        return of(t -> true, stored, max);
    }

    static <T> EnergyProvider<T> of(Obj2Bool<T> hasEnergy, Obj2Double<T> stored, Obj2Double<T> max) {
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

    default boolean hasEnergy(T t) {
        return true;
    }

    double getStored(T t);

    double getMax(T t);

}
