package badasintended.megane.api.provider;

import badasintended.megane.api.function.Functions.Obj2Double;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;

public interface CauldronFluidProvider extends ContextAwareProvider {

    static CauldronFluidProvider of(Fluid fluid, Double capacity, Obj2Double<BlockState> stored) {
        return new CauldronFluidProvider() {
            @Override
            public Fluid getFluid(BlockState state) {
                return fluid;
            }

            @Override
            public double getStored(BlockState state) {
                return stored.apply(state);
            }

            @Override
            public double getMax(BlockState state) {
                return capacity;
            }
        };
    }

    Fluid getFluid(BlockState state);

    double getStored(BlockState state);

    double getMax(BlockState state);

}
