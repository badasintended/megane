package badasintended.megane.impl.mixin.modern_industrialization;

import aztech.modern_industrialization.blocks.storage.tank.TankBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TankBlockEntity.class)
public interface ATankBlockEntity {

    @Accessor
    long getCapacity();

}
