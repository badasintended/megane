package badasintended.megane.impl.mixin.modern_industrialization;

import aztech.modern_industrialization.machines.impl.MachineBlockEntity;
import aztech.modern_industrialization.machines.impl.MachineFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(MachineBlockEntity.class)
public interface AMachineBlockEntity {

    @Accessor
    MachineFactory getFactory();

    @Accessor
    int getUsedEnergy();

    @Accessor
    int getRecipeEnergy();

    @Accessor
    long getStoredEu();

    @Invoker("getMaxStoredEu")
    long getMaxEu();

}
