package badasintended.megane.impl.mixin.extra_generators;

import io.github.lucaargolo.extragenerators.common.blockentity.FluidGeneratorBlockEntity;
import io.github.lucaargolo.extragenerators.common.blockentity.FluidItemGeneratorBlockEntity;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({
    FluidGeneratorBlockEntity.class,
    FluidItemGeneratorBlockEntity.class
})
public interface FluidInvHolder {

    @Accessor
    @SuppressWarnings({"deprecation", "UnstableApiUsage"})
    SingleVariantStorage<FluidVariant> getFluidInv();

}
