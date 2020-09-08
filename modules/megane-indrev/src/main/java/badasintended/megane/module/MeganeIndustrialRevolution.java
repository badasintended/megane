package badasintended.megane.module;

import badasintended.megane.api.MeganeEntrypoint;
import badasintended.megane.api.registry.ProgressTooltipRegistry;
import me.steven.indrev.blockentities.crafters.CraftingMachineBlockEntity;
import net.minecraft.screen.PropertyDelegate;

public class MeganeIndustrialRevolution implements MeganeEntrypoint {

    @Override
    public void initialize() {
        ProgressTooltipRegistry.register(CraftingMachineBlockEntity.class, ProgressTooltipRegistry.Provider.of(
            b -> b.getInventoryComponent().getInventory().getInputSlots(),
            b -> b.getInventoryComponent().getInventory().getOutputSlots(),
            (b, i) -> b.getInventoryComponent().getInventory().getStack(i),
            b -> {
                PropertyDelegate property = b.getPropertyDelegate();
                double max = property.get(4);
                double current = property.get(3);
                return (int) ((max - current) / max * 100);
            }
        ));
    }

}
