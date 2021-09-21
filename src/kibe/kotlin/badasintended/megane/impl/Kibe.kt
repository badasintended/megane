package badasintended.megane.impl

import badasintended.megane.api.provider.FluidInfoProvider
import badasintended.megane.api.provider.FluidProvider
import badasintended.megane.api.registry.MeganeClientRegistrar
import badasintended.megane.api.registry.MeganeRegistrar
import badasintended.megane.impl.mixin.kibe.TankHolder
import badasintended.megane.kotlin.api.KMeganeModule
import badasintended.megane.kotlin.api.fluid
import io.github.lucaargolo.kibe.fluids.LIQUID_XP
import io.github.lucaargolo.kibe.fluids.miscellaneous.LiquidXpFluid

inline val TankHolder.tank get() = megane_getTank()!!

class Kibe : KMeganeModule {

    @Suppress("UnstableApiUsage")
    override fun MeganeRegistrar.common() {
        fluid(TankHolder::class, FluidProvider.of(
            { 1 },
            { it, _ -> it.tank.variant.fluid },
            { it, _ -> it.tank.amount / 81.0 },
            { it, _ -> it.tank.capacity / 81.0 }
        ))
    }

    override fun MeganeClientRegistrar.client() {
        fluid(LiquidXpFluid::class, FluidInfoProvider.of(0x55FF55, LIQUID_XP.fluidBlock.name))
    }

}