package lol.bai.megane.module.kibe.provider;

import io.github.lucaargolo.kibe.fluids.miscellaneous.ModdedFluid;
import lol.bai.megane.api.provider.FluidInfoProvider;
import net.minecraft.text.Text;

public class ModdedFluidInfoProvider<T extends ModdedFluid> extends FluidInfoProvider<T> {

    private final int color;

    public ModdedFluidInfoProvider(int color) {
        this.color = color;
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public Text getName() {
        return getObject().getFluidBlock().getName();
    }

}
