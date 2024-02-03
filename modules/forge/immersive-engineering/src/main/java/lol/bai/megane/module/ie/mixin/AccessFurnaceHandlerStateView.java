package lol.bai.megane.module.ie.mixin;

import blusunrize.immersiveengineering.common.blocks.multiblocks.logic.FurnaceHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FurnaceHandler.StateView.class)
public interface AccessFurnaceHandlerStateView {

    @Accessor("this$0")
    FurnaceHandler<?> megane_FurnaceHandler_this();

}
