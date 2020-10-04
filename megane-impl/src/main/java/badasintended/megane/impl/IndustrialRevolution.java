package badasintended.megane.impl;

import badasintended.megane.api.MeganeEntrypoint;
import badasintended.megane.api.registry.FluidTooltipRegistry;
import badasintended.megane.api.registry.ProgressTooltipRegistry;
import me.steven.indrev.blockentities.MachineBlockEntity;
import me.steven.indrev.blockentities.crafters.CraftingMachineBlockEntity;
import me.steven.indrev.blockentities.storage.TankBlockEntity;
import net.minecraft.screen.PropertyDelegate;

public class IndustrialRevolution implements MeganeEntrypoint {

    private static final String[] DEP = new String[]{"indrev"};

    @Override
    public String[] dependencies() {
        return DEP;
    }

    @Override
    public void initialize() {
        //noinspection ConstantConditions
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

        FluidTooltipRegistry.register(MachineBlockEntity.class, FluidTooltipRegistry.Provider.of(
            t -> t.getFluidComponent() != null,
            t -> t.getFluidComponent().getTankCount(),
            (t, i) -> t.getFluidComponent().getInvFluid(i).getName(),
            (t, i) -> (double) t.getFluidComponent().getInvFluid(i).getAmount_F().asInt(1000),
            (t, i) -> (double) t.getFluidComponent().getMaxAmount_F(i).asInt(1000)
        ));

        FluidTooltipRegistry.register(TankBlockEntity.class, FluidTooltipRegistry.Provider.of(
            t -> t.getFluidComponent().getTankCount(),
            (t, i) -> t.getFluidComponent().getInvFluid(i).getName(),
            (t, i) -> (double) t.getFluidComponent().getInvFluid(i).getAmount_F().asInt(1000),
            (t, i) -> (double) t.getFluidComponent().getMaxAmount_F(i).asInt(1000)
        ));
    }

}
