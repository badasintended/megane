package badasintended.megane.impl;

import badasintended.megane.api.MeganeEntrypoint;
import badasintended.megane.api.provider.FluidProvider;
import badasintended.megane.api.provider.ProgressProvider;
import me.steven.indrev.blockentities.MachineBlockEntity;
import me.steven.indrev.blockentities.crafters.CraftingMachineBlockEntity;
import me.steven.indrev.blockentities.storage.TankBlockEntity;
import net.minecraft.screen.PropertyDelegate;

import static badasintended.megane.api.registry.TooltipRegistry.FLUID;
import static badasintended.megane.api.registry.TooltipRegistry.PROGRESS;

public class IndustrialRevolution implements MeganeEntrypoint {

    private static final String[] DEP = new String[]{"indrev"};

    @Override
    public String[] dependencies() {
        return DEP;
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void initialize() {
        PROGRESS.register(CraftingMachineBlockEntity.class, ProgressProvider.of(
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

        FLUID.register(MachineBlockEntity.class, FluidProvider.of(
            t -> t.getFluidComponent() != null,
            t -> t.getFluidComponent().getTankCount(),
            (t, i) -> t.getFluidComponent().getInvFluid(i).getName(),
            (t, i) -> (double) t.getFluidComponent().getInvFluid(i).getAmount_F().asInt(1000),
            (t, i) -> (double) t.getFluidComponent().getMaxAmount_F(i).asInt(1000)
        ));

        FLUID.register(TankBlockEntity.class, FluidProvider.of(
            t -> t.getFluidComponent().getTankCount(),
            (t, i) -> t.getFluidComponent().getInvFluid(i).getName(),
            (t, i) -> (double) t.getFluidComponent().getInvFluid(i).getAmount_F().asInt(1000),
            (t, i) -> (double) t.getFluidComponent().getMaxAmount_F(i).asInt(1000)
        ));
    }

}
