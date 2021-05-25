package badasintended.megane.impl;

import alexiil.mc.lib.attributes.fluid.volume.FluidKeys;
import badasintended.megane.api.MeganeModule;
import badasintended.megane.api.provider.FluidInfoProvider;
import badasintended.megane.api.registry.MeganeClientRegistrar;
import io.github.lucaargolo.kibe.fluids.miscellaneous.ModdedFluid;

public class Kibe implements MeganeModule {

    @Override
    public void registerClient(MeganeClientRegistrar registrar) {
        registrar.fluid(ModdedFluid.class, FluidInfoProvider.of(
            f -> FluidKeys.get(f).name.getStyle().getColor().getRgb(), f -> FluidKeys.get(f).name
        ));
    }

}
