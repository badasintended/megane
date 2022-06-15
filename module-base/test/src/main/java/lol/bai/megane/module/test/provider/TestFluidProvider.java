package lol.bai.megane.module.test.provider;

import lol.bai.megane.api.provider.FluidProvider;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import org.jetbrains.annotations.Nullable;

public class TestFluidProvider extends FluidProvider<ChestBlockEntity> {

    @Override
    public int getSlotCount() {
        return 2;
    }

    @Override
    public @Nullable Fluid getFluid(int slot) {
        return slot == 0 ? Fluids.LAVA : Fluids.WATER;
    }

    @Override
    public double getStored(int slot) {
        return slot == 0 ? 324128 : 8401347;
    }

    @Override
    public double getMax(int slot) {
        return slot == 0 ? 71370150 : 274015403;
    }

}
