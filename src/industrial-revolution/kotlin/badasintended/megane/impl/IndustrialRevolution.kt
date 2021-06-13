package badasintended.megane.impl

import alexiil.mc.lib.attributes.fluid.volume.FluidKeys
import badasintended.megane.api.provider.FluidInfoProvider
import badasintended.megane.api.provider.FluidProvider
import badasintended.megane.api.provider.ProgressProvider
import badasintended.megane.api.registry.MeganeClientRegistrar
import badasintended.megane.api.registry.MeganeRegistrar
import badasintended.megane.kotlin.api.KMeganeModule
import badasintended.megane.kotlin.api.fluid
import badasintended.megane.kotlin.api.progress
import me.steven.indrev.blockentities.MachineBlockEntity
import me.steven.indrev.blockentities.crafters.CompressorFactoryBlockEntity
import me.steven.indrev.blockentities.crafters.CraftingMachineBlockEntity
import me.steven.indrev.blockentities.crafters.ElectricFurnaceFactoryBlockEntity
import me.steven.indrev.blockentities.crafters.SolidInfuserFactoryBlockEntity
import me.steven.indrev.blockentities.modularworkbench.ModularWorkbenchBlockEntity
import me.steven.indrev.blockentities.storage.TankBlockEntity
import me.steven.indrev.fluids.BaseFluid
import kotlin.reflect.KClass

class IndustrialRevolution : KMeganeModule {
    /**
     * @see me.steven.indrev.utils.createProcessBar
     */
    private fun <T : MachineBlockEntity<*>> MeganeRegistrar.progress(clazz: KClass<T>, amount: Int, currentIndex: Int, maxIndex: Int) {
        progress(clazz, ProgressProvider.of(
            { it.inventoryComponent!!.inventory.inputSlots },
            { it.inventoryComponent!!.inventory.outputSlots },
            { it, i -> it.inventoryComponent!!.inventory.getStack(i) },
            {
                val property = it.propertyDelegate
                var result = 0
                for (i in 0 until amount) {
                    val current = property[currentIndex + i * 2].toDouble()
                    val max = property[maxIndex + i * 2].toDouble()
                    result = result.coerceAtLeast((current / max * 100).toInt())
                }
                result
            }
        ))
    }

    override fun MeganeRegistrar.common() {
        fluid(MachineBlockEntity::class, FluidProvider.conditional(
            { it.fluidComponent != null },
            { it.fluidComponent!!.tankCount },
            { it, i -> it.fluidComponent!!.getInvFluid(i).rawFluid },
            { it, i -> it.fluidComponent!!.getInvFluid(i).amount_F.asInt(1000).toDouble() },
            { it, i -> it.fluidComponent!!.getMaxAmount_F(i).asInt(1000).toDouble() }
        ))
        fluid(TankBlockEntity::class, FluidProvider.of(
            { it.fluidComponent.tankCount },
            { it, i -> it.fluidComponent.getInvFluid(i).rawFluid },
            { it, i -> it.fluidComponent.getInvFluid(i).amount_F.asInt(1000).toDouble() },
            { it, i -> it.fluidComponent.getMaxAmount_F(i).asInt(1000).toDouble() }
        ))
        progress(CraftingMachineBlockEntity::class, 1, 4, 5)
        progress(CompressorFactoryBlockEntity::class, 5, 4, 5)
        progress(CompressorFactoryBlockEntity::class, 5, 4, 5)
        progress(SolidInfuserFactoryBlockEntity::class, 5, 4, 5)
        progress(ElectricFurnaceFactoryBlockEntity::class, 5, 4, 5)
        progress(ModularWorkbenchBlockEntity::class, 1, 2, 3)
    }

    override fun MeganeClientRegistrar.client() {
        fluid(BaseFluid::class, FluidInfoProvider.of(
            { f -> FluidKeys.get(f).renderColor },
            { f -> FluidKeys.get(f).name }
        ))
        energy("indrev", 0x3B4ADE, "LF")
    }
}