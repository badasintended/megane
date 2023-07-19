package lol.bai.megane.module.create.mixin;

import com.simibubi.create.content.kinetics.millstone.MillingRecipe;
import com.simibubi.create.content.kinetics.millstone.MillstoneBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MillstoneBlockEntity.class)
public interface AccessMillstoneProgressProvider {

    @Accessor("lastRecipe")
    MillingRecipe megane_lastRecipe();

}
