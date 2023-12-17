package lol.bai.megane.module.ie.mixin;

import java.util.List;

import blusunrize.immersiveengineering.common.blocks.stone.FurnaceLikeBlockEntity;
import lol.bai.megane.module.ie.provider.FurnaceLikeProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FurnaceLikeBlockEntity.class)
public interface AccessFurnaceLikeBlockEntity {

    @Accessor("fuelSlot")
    int megane_fuelSlot();

    @Accessor("inputs")
    List<FurnaceLikeProvider.Slot> megane_inputs();

    @Accessor("outputs")
    List<FurnaceLikeProvider.Slot> megane_outputs();

}
