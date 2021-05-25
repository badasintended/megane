package badasintended.megane.impl.mixin.modern_industrialization;

import java.util.List;

import aztech.modern_industrialization.machines.blockentities.multiblocks.EnergyFromFluidMultiblockBlockEntity;
import aztech.modern_industrialization.machines.components.EnergyComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EnergyFromFluidMultiblockBlockEntity.class)
public interface EnergyOutputsComponentHolder {

    @Accessor
    List<EnergyComponent> getEnergyOutputs();

}
