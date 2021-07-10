package badasintended.megane.impl.mixin.modern_industrialization;

import aztech.modern_industrialization.fluid.CraftingFluid;
import aztech.modern_industrialization.fluid.CraftingFluidBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CraftingFluid.class)
public interface ACraftingFluid {

    @Accessor
    CraftingFluidBlock getBlock();

}
