package lol.bai.megane.module.vanilla.mixin;

import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.inventory.SimpleInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractHorseEntity.class)
public interface AccessorAbstractHorseEntity {

    @Accessor
    SimpleInventory getItems();

}
