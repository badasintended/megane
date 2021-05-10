package badasintended.megane.impl;

import alexiil.mc.lib.attributes.fluid.volume.FluidKeys;
import badasintended.megane.api.MeganeModule;
import badasintended.megane.api.provider.FluidInfoProvider;
import io.github.lucaargolo.kibe.fluids.miscellaneous.ModdedFluid;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import static badasintended.megane.api.registry.TooltipRegistry.FLUID_INFO;

public class Kibe implements MeganeModule {

    @Override
    @Environment(EnvType.CLIENT)
    @SuppressWarnings("ConstantConditions")
    public void initializeClient() {
        FLUID_INFO.register(ModdedFluid.class, FluidInfoProvider.of(
            f -> FluidKeys.get(f).name.getStyle().getColor().getRgb(), f -> FluidKeys.get(f).name
        ));
    }

}
