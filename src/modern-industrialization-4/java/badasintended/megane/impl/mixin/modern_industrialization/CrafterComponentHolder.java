package badasintended.megane.impl.mixin.modern_industrialization;

import aztech.modern_industrialization.machines.blockentities.AbstractCraftingMachineBlockEntity;
import aztech.modern_industrialization.machines.blockentities.multiblocks.AbstractCraftingMultiblockBlockEntity;
import aztech.modern_industrialization.machines.components.CrafterComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({
    AbstractCraftingMachineBlockEntity.class,
    AbstractCraftingMultiblockBlockEntity.class
})
public interface CrafterComponentHolder {

    @Accessor
    CrafterComponent getCrafter();

    @Mixin(CrafterComponent.class)
    interface Inventory {

        @Accessor
        CrafterComponent.Inventory getInventory();

    }

}
