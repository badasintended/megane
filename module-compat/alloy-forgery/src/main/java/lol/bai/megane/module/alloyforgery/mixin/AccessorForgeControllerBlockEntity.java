package lol.bai.megane.module.alloyforgery.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import wraith.alloyforgery.block.ForgeControllerBlockEntity;
import wraith.alloyforgery.forges.ForgeDefinition;

@Mixin(ForgeControllerBlockEntity.class)
public interface AccessorForgeControllerBlockEntity {

    @Accessor
    ForgeDefinition getForgeDefinition();

    @Accessor
    int getFuel();


}
