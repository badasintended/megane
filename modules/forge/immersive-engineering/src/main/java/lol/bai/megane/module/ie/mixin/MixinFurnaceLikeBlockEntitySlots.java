package lol.bai.megane.module.ie.mixin;

import lol.bai.megane.module.ie.provider.FurnaceLikeProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(targets = {
    "blusunrize.immersiveengineering.common.blocks.stone.FurnaceLikeBlockEntity$InputSlot",
    "blusunrize.immersiveengineering.common.blocks.stone.FurnaceLikeBlockEntity$OutputSlot"})
public class MixinFurnaceLikeBlockEntitySlots implements FurnaceLikeProvider.Slot {

    @Final
    @Shadow(remap = false)
    private int slotIndex;

    @Override
    public int megane_slotIndex() {
        return slotIndex;
    }

}
