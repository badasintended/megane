package badasintended.megane.api.provider;

import badasintended.megane.api.function.Functions.Obj2Bool;
import badasintended.megane.api.function.Functions.Obj2Int;
import badasintended.megane.api.function.Functions.ObjInt2Double;
import badasintended.megane.api.function.Functions.ObjInt2Obj;
import net.minecraft.fluid.Fluid;
import org.jetbrains.annotations.Nullable;

public interface FluidProvider<T> extends ContextAwareProvider {

    static <T> FluidProvider<T> of(Obj2Int<T> slotCount, ObjInt2Obj<T, Fluid> fluid, ObjInt2Double<T> stored, ObjInt2Double<T> max) {
        return conditional(t -> true, slotCount, fluid, stored, max);
    }

    static <T> FluidProvider<T> conditional(Obj2Bool<T> hasFluid, Obj2Int<T> slotCount, ObjInt2Obj<T, Fluid> fluid, ObjInt2Double<T> stored, ObjInt2Double<T> max) {
        return new FluidProvider<>() {
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

    default boolean hasFluid(T t) {
        return true;
    }

    int getSlotCount(T t);

    @Nullable
    Fluid getFluid(T t, int slot);

    double getStored(T t, int slot);

    double getMax(T t, int slot);

}
