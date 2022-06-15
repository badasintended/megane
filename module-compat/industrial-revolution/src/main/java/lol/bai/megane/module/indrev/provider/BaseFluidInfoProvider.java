package lol.bai.megane.module.indrev.provider;

import lol.bai.megane.api.provider.FluidInfoProvider;
import me.steven.indrev.fluids.BaseFluid;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.minecraft.text.Text;

public class BaseFluidInfoProvider extends FluidInfoProvider<BaseFluid> {

    @Override
    public int getColor() {
        return FluidRenderHandlerRegistry.INSTANCE
            .get(getObject())
            .getFluidColor(getWorld(), getPos(), getWorld().getFluidState(getPos()));
    }

    @Override
    public Text getName() {
        return getObject().getBlock().invoke().getName();
    }

}
