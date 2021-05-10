package badasintended.megane.impl.mixin.fabric_furnaces;

import draylar.fabricfurnaces.entity.BaseFurnaceEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BaseFurnaceEntity.class)
public interface ABaseFurnaceEntity {

    @Accessor("cookTime")
    int getCookTimeProgress();

}
