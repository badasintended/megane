package badasintended.megane.impl

import badasintended.megane.api.provider.FluidProvider
import badasintended.megane.api.provider.InventoryProvider
import badasintended.megane.api.registry.MeganeRegistrar
import badasintended.megane.impl.mixin.extra_generators.FluidInvHolder
import badasintended.megane.impl.mixin.extra_generators.ItemInvHolder
import badasintended.megane.kotlin.api.KMeganeModule
import badasintended.megane.kotlin.api.fluid
import badasintended.megane.kotlin.api.inventory

class ExtraGenerators : KMeganeModule {

    override fun MeganeRegistrar.common() {
        fluid(FluidInvHolder::class, FluidProvider.of(
            { it.fluidInv.tankCount },
            { it, i -> it.fluidInv.getInvFluid(i).rawFluid },
            { it, i -> it.fluidInv.getInvFluid(i).amount().asLong(1000).toDouble() },
            { it, i -> it.fluidInv.getMaxAmount_F(i).asLong(1000).toDouble() }
        ))

        inventory(ItemInvHolder::class, InventoryProvider.of(
            { it.itemInv.slotCount },
            { it, i -> it.itemInv.getInvStack(i) }
        ))
    }

}