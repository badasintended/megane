package badasintended.megane.impl.mixin.fabricfurnaces;

import draylar.fabricfurnaces.entity.BaseFurnaceEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BaseFurnaceEntity.class)
public interface AccessorBaseFurnaceEntity {

    @Accessor("cookTime")
    int getCookTimeProgress();

}
