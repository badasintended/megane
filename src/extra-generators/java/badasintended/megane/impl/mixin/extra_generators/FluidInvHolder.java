package badasintended.megane.impl.mixin.extra_generators;

import alexiil.mc.lib.attributes.fluid.impl.SimpleFixedFluidInv;
import io.github.lucaargolo.extragenerators.common.blockentity.FluidGeneratorBlockEntity;
import io.github.lucaargolo.extragenerators.common.blockentity.FluidItemGeneratorBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({
    FluidGeneratorBlockEntity.class,
    FluidItemGeneratorBlockEntity.class
})
public interface FluidInvHolder {

    @Accessor
    SimpleFixedFluidInv getFluidInv();

}
