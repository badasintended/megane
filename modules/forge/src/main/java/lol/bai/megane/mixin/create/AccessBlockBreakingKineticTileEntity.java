package lol.bai.megane.mixin.create;

import com.simibubi.create.content.kinetics.base.BlockBreakingKineticBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BlockBreakingKineticBlockEntity.class)
public interface AccessBlockBreakingKineticTileEntity {

    @Accessor("destroyProgress")
    int megane_destroyProgress();

}
