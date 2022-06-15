package lol.bai.megane.module.vanilla.provider;

import lol.bai.megane.api.provider.CauldronProvider;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import org.jetbrains.annotations.Nullable;

public class LavaCauldronProvider extends CauldronProvider {

    @Override
    public @Nullable Fluid getFluid(int slot) {
        return Fluids.LAVA;
    }

    @Override
    public double getStored(int slot) {
        return 1000;
    }

}
