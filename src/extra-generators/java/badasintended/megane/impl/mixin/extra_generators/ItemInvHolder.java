package badasintended.megane.impl.mixin.extra_generators;

import io.github.lucaargolo.extragenerators.common.blockentity.ColorfulGeneratorBlockEntity;
import io.github.lucaargolo.extragenerators.common.blockentity.FluidGeneratorBlockEntity;
import io.github.lucaargolo.extragenerators.common.blockentity.FluidItemGeneratorBlockEntity;
import io.github.lucaargolo.extragenerators.common.blockentity.ItemGeneratorBlockEntity;
import io.github.lucaargolo.extragenerators.utils.SimpleSidedInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({
    ColorfulGeneratorBlockEntity.class,
    FluidGeneratorBlockEntity.class,
    FluidItemGeneratorBlockEntity.class,
    ItemGeneratorBlockEntity.class
})
public interface ItemInvHolder {

    @Accessor
    SimpleSidedInventory getItemInv();

}
