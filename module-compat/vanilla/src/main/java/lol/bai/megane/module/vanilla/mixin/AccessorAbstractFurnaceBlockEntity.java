package lol.bai.megane.module.vanilla.mixin;

import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractFurnaceBlockEntity.class)
public interface AccessorAbstractFurnaceBlockEntity {

    @Accessor
    int getCookTime();

    @Accessor
    int getCookTimeTotal();

}
