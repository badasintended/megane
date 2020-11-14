package badasintended.megane.impl.mixin.artofalchemy;

import com.cumulusmc.artofalchemy.blockentity.BlockEntityCalcinator;
import com.cumulusmc.artofalchemy.blockentity.BlockEntityDissolver;
import com.cumulusmc.artofalchemy.blockentity.BlockEntityProjector;
import com.cumulusmc.artofalchemy.blockentity.BlockEntitySynthesizer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({
    BlockEntityCalcinator.class,
    BlockEntityDissolver.class,
    BlockEntitySynthesizer.class,
    BlockEntityProjector.class
})
public interface AProgress {

    @Accessor
    int getProgress();

    @Accessor
    int getMaxProgress();

}
