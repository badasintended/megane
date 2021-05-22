package badasintended.megane.impl.mixin.modern_industrialization;

import aztech.modern_industrialization.machines.blockentities.multiblocks.AbstractCraftingMultiblockBlockEntity;
import aztech.modern_industrialization.machines.blockentities.multiblocks.EnergyFromFluidMultiblockBlockEntity;
import aztech.modern_industrialization.machines.blockentities.multiblocks.SteamBoilerMultiblockBlockEntity;
import aztech.modern_industrialization.machines.components.MultiblockInventoryComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({
    AbstractCraftingMultiblockBlockEntity.class,
    EnergyFromFluidMultiblockBlockEntity.class,
    SteamBoilerMultiblockBlockEntity.class
})
public interface MultiblockInventoryComponentHolder {

    @Accessor
    MultiblockInventoryComponent getInventory();

}
