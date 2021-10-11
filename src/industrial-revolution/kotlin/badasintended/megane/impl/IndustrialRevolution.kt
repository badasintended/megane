package badasintended.megane.impl

import badasintended.megane.api.provider.EnergyProvider
import badasintended.megane.api.provider.FluidInfoProvider
import badasintended.megane.api.provider.FluidProvider
import badasintended.megane.api.provider.ProgressProvider
import badasintended.megane.api.registry.MeganeClientRegistrar
import badasintended.megane.api.registry.MeganeRegistrar
import badasintended.megane.kotlin.api.KMeganeModule
import badasintended.megane.kotlin.api.energy
import badasintended.megane.kotlin.api.fluid
import badasintended.megane.kotlin.api.progress
import me.steven.indrev.api.machines.Tier
import me.steven.indrev.blockentities.MachineBlockEntity
import me.steven.indrev.blockentities.crafters.CraftingMachineBlockEntity
import me.steven.indrev.blockentities.modularworkbench.ModularWorkbenchBlockEntity
import me.steven.indrev.blockentities.storage.TankBlockEntity
import me.steven.indrev.fluids.BaseFluid
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry

class IndustrialRevolution : KMeganeModule {

    @Suppress("UnstableApiUsage")
    override fun MeganeRegistrar.common() {
        energy(MachineBlockEntity::class, EnergyProvider.of(
            { if (it.tier == Tier.CREATIVE) -1.0 else it.guiSyncableComponent!!.get<Long>(MachineBlockEntity.ENERGY_ID).toDouble() },
            { if (it.tier == Tier.CREATIVE) -1.0 else it.getCapacity().toDouble() }
        ))

        fluid(MachineBlockEntity::class, FluidProvider.conditional(
            { it.fluidComponent != null },
            { it.fluidComponent!!.tankCount },
            { it, i -> it.fluidComponent!![i].variant.fluid },
            { it, i -> it.fluidComponent!![i].amount / 81.0 },
            { it, i -> it.fluidComponent!!.getTankCapacity(i) / 81.0 }
        ))

        fluid(TankBlockEntity::class, FluidProvider.of(
            { it.fluidComponent.tankCount },
            { it, i -> it.fluidComponent[i].variant.fluid },
            { it, i -> it.fluidComponent[i].amount / 81.0 },
            { it, i -> it.fluidComponent.getTankCapacity(i) / 81.0 }
        ))

        progress(CraftingMachineBlockEntity::class, ProgressProvider.of(
            { it.inventoryComponent!!.inventory.inputSlots },
            { it.inventoryComponent!!.inventory.outputSlots },
            { it, i -> it.inventoryComponent!!.inventory.getStack(i) },
            {
                var result = 0
                for (component in it.craftingComponents) {
                    val current = component.processTime.toDouble()
                    val max = component.totalProcessTime.toDouble()
                    result = result.coerceAtLeast((current / max * 100).toInt())
                }
                result
            }
        ))

        progress(ModularWorkbenchBlockEntity::class, ProgressProvider.of(
            { it.inventoryComponent!!.inventory.inputSlots },
            { it.inventoryComponent!!.inventory.outputSlots },
            { it, i -> it.inventoryComponent!!.inventory.getStack(i) },
            { (it.moduleProcessTime.toDouble() / it.moduleMaxProcessTime * 100).toInt() }
        ))
    }

    override fun MeganeClientRegistrar.client() {
        fluid(BaseFluid::class, FluidInfoProvider.of(
            { FluidRenderHandlerRegistry.INSTANCE[it].getFluidColor(null, null, null) },
            { it.block().name }
        ))

        energy("indrev", 0x3B4ADE, "LF")
    }
}