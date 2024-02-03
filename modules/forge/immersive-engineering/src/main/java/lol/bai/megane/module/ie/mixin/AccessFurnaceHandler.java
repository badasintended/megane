package lol.bai.megane.module.ie.mixin;

import java.util.List;

import blusunrize.immersiveengineering.common.blocks.multiblocks.logic.FurnaceHandler;
import lol.bai.megane.module.ie.provider.FurnaceProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FurnaceHandler.class)
public interface AccessFurnaceHandler {

    @Accessor("fuelSlot")
    int megane_fuelSlot();

    @Accessor("inputs")
    List<FurnaceProvider.Slot> megane_inputs();

    @Accessor("outputs")
    List<FurnaceProvider.Slot> megane_outputs();

}
