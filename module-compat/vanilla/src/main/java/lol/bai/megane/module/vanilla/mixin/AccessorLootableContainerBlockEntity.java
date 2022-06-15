package lol.bai.megane.module.vanilla.mixin;

import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LootableContainerBlockEntity.class)
public interface AccessorLootableContainerBlockEntity {

    @Accessor
    Identifier getLootTableId();

}
