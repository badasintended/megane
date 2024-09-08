package lol.bai.megane.module.mekanism.mixin;

import mekanism.common.inventory.container.slot.ContainerSlotType;
import mekanism.common.inventory.slot.BasicInventorySlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BasicInventorySlot.class)
public interface AccessBasicInventorySlot {

    @Invoker("getSlotType")
    ContainerSlotType megane_getSlotType();

}
