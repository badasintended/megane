package badasintended.megane.impl

import alexiil.mc.lib.attributes.fluid.volume.FluidKeys
import badasintended.megane.api.provider.FluidInfoProvider
import badasintended.megane.api.registry.MeganeClientRegistrar
import badasintended.megane.kotlin.api.KMeganeModule
import io.github.lucaargolo.kibe.fluids.miscellaneous.ModdedFluid

class Kibe : KMeganeModule {
    override fun MeganeClientRegistrar.client() {
        fluid(ModdedFluid::class.java, FluidInfoProvider.of(
            { f -> FluidKeys.get(f).name.style.color!!.rgb },
            { f -> FluidKeys.get(f).name }
        ))
    }
}