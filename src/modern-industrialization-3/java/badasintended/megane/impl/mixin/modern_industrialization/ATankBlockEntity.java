package badasintended.megane.impl.mixin.modern_industrialization;

import alexiil.mc.lib.attributes.fluid.volume.FluidKey;
import aztech.modern_industrialization.blocks.tank.TankBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

// TODO: Remove
@Mixin(TankBlockEntity.class)
public interface ATankBlockEntity {

    @Accessor
    FluidKey getFluid();

    @Accessor
    int getAmount();

    @Accessor
    int getCapacity();

}