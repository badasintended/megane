package badasintended.megane.module;

import badasintended.megane.api.MeganeApi;
import badasintended.megane.api.registry.ProgressTooltipRegistry;
import me.steven.indrev.blockentities.crafters.CraftingMachineBlockEntity;
import net.minecraft.screen.PropertyDelegate;

public class MeganeIndustrialRevolution implements MeganeApi {

    @Override
    public void initialize() {
        ProgressTooltipRegistry.register(
            CraftingMachineBlockEntity.class,
            b -> b.getInventoryComponent().getInventory().getInputSlots().length,
            b -> b.getInventoryComponent().getInventory().getOutputSlots().length,
            (b, i) -> b.getInventoryComponent().getInventory().getInputInventory().getStack(i),
            (b, i) -> b.getInventoryComponent().getInventory().getOutputInventory().getStack(i),
            b -> {
                PropertyDelegate property = b.getPropertyDelegate();
                double max = property.get(4);
                double current = property.get(3);
                return (int) ((max - current) / max * 100);
            }
        );
    }

}
