package lol.bai.megane.module.vanilla.provider;

import lol.bai.megane.api.provider.FluidInfoProvider;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class LavaFluidInfoProvider extends FluidInfoProvider<LavaFluid> {

    private static final Text NAME = new TranslatableText("block.minecraft.water");

    @Override
    public int getColor() {
        return 0xD45A12;
    }

    @Override
    public Text getName() {
        return NAME;
    }

}
