package badasintended.megane.api.registry;

import badasintended.megane.api.provider.FluidInfoProvider;
import net.minecraft.fluid.Fluid;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public interface MeganeClientRegistrar {

    MeganeClientRegistrar fluid(Fluid fluid, int color, Text name);

    <T> MeganeClientRegistrar fluid(Class<T> fluid, FluidInfoProvider<T> provider);

    MeganeClientRegistrar energy(String namespace, int color, String unit, Text name);

    default MeganeClientRegistrar energy(String namespace, int color, String unit) {
        return energy(namespace, color, unit, new TranslatableText("megane.energy"));
    }

}
