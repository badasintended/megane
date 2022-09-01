package lol.bai.megane.module.create.mixin;

import com.simibubi.create.content.contraptions.components.millstone.MillingRecipe;
import com.simibubi.create.content.contraptions.components.millstone.MillstoneTileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MillstoneTileEntity.class)
public interface AccessMillstoneProgressProvider {

    @Accessor("lastRecipe")
    MillingRecipe megane_lastRecipe();

}
