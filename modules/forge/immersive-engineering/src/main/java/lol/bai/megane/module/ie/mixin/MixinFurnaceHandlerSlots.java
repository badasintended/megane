package lol.bai.megane.module.ie.mixin;

import lol.bai.megane.module.ie.provider.FurnaceProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(targets = {
    "blusunrize.immersiveengineering.common.blocks.multiblocks.logic.FurnaceHandler$InputSlot",
    "blusunrize.immersiveengineering.common.blocks.multiblocks.logic.FurnaceHandler$OutputSlot"})
public class MixinFurnaceHandlerSlots implements FurnaceProvider.Slot {

    @Final
    @Shadow(remap = false)
    private int slotIndex;

    @Override
    public int megane_slotIndex() {
        return slotIndex;
    }

}
