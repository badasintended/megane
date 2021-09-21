package badasintended.megane.impl

import badasintended.megane.api.provider.EnergyProvider
import badasintended.megane.api.provider.FluidProvider
import badasintended.megane.api.provider.InventoryProvider
import badasintended.megane.api.provider.ProgressProvider
import badasintended.megane.api.registry.MeganeRegistrar
import badasintended.megane.impl.mixin.extra_generators.FluidInvHolder
import badasintended.megane.impl.mixin.extra_generators.ItemInvHolder
import badasintended.megane.impl.util.A
import badasintended.megane.kotlin.api.KMeganeModule
import badasintended.megane.kotlin.api.energy
import badasintended.megane.kotlin.api.fluid
import badasintended.megane.kotlin.api.inventory
import badasintended.megane.kotlin.api.progress
import io.github.lucaargolo.extragenerators.common.blockentity.AbstractGeneratorBlockEntity
import io.github.lucaargolo.extragenerators.common.blockentity.ColorfulGeneratorBlockEntity
import io.github.lucaargolo.extragenerators.common.blockentity.FluidGeneratorBlockEntity
import io.github.lucaargolo.extragenerators.common.blockentity.FluidItemGeneratorBlockEntity
import io.github.lucaargolo.extragenerators.common.blockentity.ItemGeneratorBlockEntity
import io.github.lucaargolo.extragenerators.utils.FluidGeneratorFuel
import io.github.lucaargolo.extragenerators.utils.GeneratorFuel

class ExtraGenerators : KMeganeModule {

    private fun getProgress(fuel: GeneratorFuel?): Int {
        fuel!!
        return 100 - (fuel.currentBurnTime.toDouble() / fuel.burnTime.toDouble() * 100.0).toInt()
    }

    private fun getProgress(fuel: FluidGeneratorFuel?): Int {
        fuel!!
        return 100 - (fuel.currentBurnTime.toDouble() / fuel.burnTime.toDouble() * 100.0).toInt()
    }

    @Suppress("UnstableApiUsage")
    override fun MeganeRegistrar.common() {
        energy(AbstractGeneratorBlockEntity::class, EnergyProvider.of(
            { it.energyStorage.getAmount().toDouble() },
            { it.energyStorage.getCapacity().toDouble() }
        ))

        fluid(FluidInvHolder::class, FluidProvider.of(
            { 1 },
            { it, _ -> it.fluidInv.variant.fluid },
            { it, _ -> it.fluidInv.amount / 81.0 },
            { it, _ -> it.fluidInv.capacity / 81.0 }
        ))

        inventory(ItemInvHolder::class, InventoryProvider.of(
            { it.itemInv.size() },
            { it, i -> it.itemInv.getStack(i) }
        ))

        progress(ItemGeneratorBlockEntity::class, ProgressProvider.conditional(
            { it.burningFuel != null },
            { A.A_0 }, { A.A },
            { it, i -> it.itemInv.getStack(i) },
            { getProgress(it.burningFuel) }
        ))

        progress(ColorfulGeneratorBlockEntity::class, ProgressProvider.conditional(
            { it.burningFuel != null },
            { A.A_012 }, { A.A },
            { it, i -> it.itemInv.getStack(i) },
            { getProgress(it.burningFuel) }
        ))

        progress(FluidGeneratorBlockEntity::class, ProgressProvider.conditional(
            { it.burningFuel != null },
            { A.A_01 }, { A.A },
            { it, i -> it.itemInv.getStack(i) },
            { getProgress(it.burningFuel) }
        ))

        progress(FluidItemGeneratorBlockEntity::class, ProgressProvider.conditional(
            { it.burningFuel != null },
            { A.A_012 }, { A.A },
            { it, i -> it.itemInv.getStack(i) },
            { getProgress(it.burningFuel) }
        ))
    }

}