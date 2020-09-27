package badasintended.megane.impl;

import badasintended.megane.api.MeganeEntrypoint;
import badasintended.megane.api.registry.FluidTooltipRegistry;
import badasintended.megane.api.registry.ProgressTooltipRegistry;
import me.steven.indrev.blockentities.MachineBlockEntity;
import me.steven.indrev.blockentities.crafters.*;
import me.steven.indrev.blockentities.storage.TankBlockEntity;
import me.steven.indrev.components.FluidComponent;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.screen.PropertyDelegate;

import java.util.function.Function;

public class MeganeIndustrialRevolution implements MeganeEntrypoint {

    private static final String[] DEP = new String[]{"indrev"};

    @Override
    public String[] dependencies() {
        return DEP;
    }

    private <T extends BlockEntity> void fluid(Class<T> clazz, Function<T, FluidComponent> getter) {
        FluidTooltipRegistry.register(clazz, FluidTooltipRegistry.Provider.of(
            t -> getter.apply(t).getTankCount(),
            (t, i) -> getter.apply(t).getInvFluid(i).getName(),
            (t, i) -> (double) getter.apply(t).getInvFluid(i).getAmount_F().asInt(1000),
            (t, i) -> (double) getter.apply(t).getMaxAmount_F(i).asInt(1000)
        ));
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

        fluid(FluidInfuserBlockEntity.class, MachineBlockEntity::getFluidComponent);
        fluid(CondenserBlockEntity.class, MachineBlockEntity::getFluidComponent);
        fluid(SmelterBlockEntity.class, MachineBlockEntity::getFluidComponent);
        fluid(TankBlockEntity.class, TankBlockEntity::getFluidComponent);
    }

}
