package badasintended.megane.impl.mixin.modern_industrialization;

import alexiil.mc.lib.attributes.fluid.volume.FluidKey;
import aztech.modern_industrialization.blocks.tank.TankBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TankBlockEntity.class)
public interface AccessorTankBlockEntity {

    @Accessor
    FluidKey getFluid();

    @Accessor
    int getAmount();

    @Accessor
    int getCapacity();

}
