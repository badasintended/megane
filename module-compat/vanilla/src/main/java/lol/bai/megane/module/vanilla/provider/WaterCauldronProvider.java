package lol.bai.megane.module.vanilla.provider;

import lol.bai.megane.api.provider.CauldronProvider;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import org.jetbrains.annotations.Nullable;

public class WaterCauldronProvider extends CauldronProvider {

    @Override
    public @Nullable Fluid getFluid(int slot) {
        return Fluids.WATER;
    }

    @Override
    public double getStored(int slot) {
        return getState().get(LeveledCauldronBlock.LEVEL) * 1000.0 / 3;
    }

}
