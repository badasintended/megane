package lol.bai.megane.module.vanilla.provider;

import lol.bai.megane.api.provider.FluidInfoProvider;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.text.Text;

public class WaterFluidInfoProvider extends FluidInfoProvider<WaterFluid> {

    private static final Text NAME = Text.literal("block.minecraft.water");

    @Override
    public int getColor() {
        return getWorld().getBiome(getPos()).value().getWaterColor();
    }

    @Override
    public Text getName() {
        return NAME;
    }

}
