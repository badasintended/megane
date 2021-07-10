package badasintended.megane.impl.mixin.modern_industrialization;

import java.util.List;

import aztech.modern_industrialization.machines.blockentities.multiblocks.DistillationTowerBlockEntity;
import aztech.modern_industrialization.machines.blockentities.multiblocks.ElectricBlastFurnaceBlockEntity;
import aztech.modern_industrialization.machines.blockentities.multiblocks.ElectricCraftingMultiblockBlockEntity;
import aztech.modern_industrialization.machines.components.EnergyComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({
    DistillationTowerBlockEntity.class,
    ElectricBlastFurnaceBlockEntity.class,
    ElectricCraftingMultiblockBlockEntity.class
})
public interface EnergyInputsComponentHolder {

    @Accessor
    List<EnergyComponent> getEnergyInputs();

}
