package badasintended.megane.impl.mixin.modern_industrialization;

import aztech.modern_industrialization.machines.blockentities.AbstractStorageMachineBlockEntity;
import aztech.modern_industrialization.machines.blockentities.ElectricCraftingMachineBlockEntity;
import aztech.modern_industrialization.machines.blockentities.ElectricWaterPumpBlockEntity;
import aztech.modern_industrialization.machines.blockentities.EnergyFromFluidMachineBlockEntity;
import aztech.modern_industrialization.machines.blockentities.hatches.EnergyHatch;
import aztech.modern_industrialization.machines.components.EnergyComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({
    AbstractStorageMachineBlockEntity.class,
    ElectricCraftingMachineBlockEntity.class,
    ElectricWaterPumpBlockEntity.class,
    EnergyFromFluidMachineBlockEntity.class,
    EnergyHatch.class
})
public interface EnergyComponentHolder {

    @Accessor
    EnergyComponent getEnergy();

}
