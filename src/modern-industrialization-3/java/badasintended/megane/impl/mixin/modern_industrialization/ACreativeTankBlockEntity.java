package badasintended.megane.impl.mixin.modern_industrialization;

import alexiil.mc.lib.attributes.fluid.volume.FluidKey;
import aztech.modern_industrialization.blocks.creativetank.CreativeTankBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

// TODO: Remove
@Mixin(CreativeTankBlockEntity.class)
public interface ACreativeTankBlockEntity {

    @Accessor
    FluidKey getFluid();

}
