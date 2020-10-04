package badasintended.megane.impl;

import aztech.modern_industrialization.blocks.creativetank.CreativeTankBlockEntity;
import aztech.modern_industrialization.blocks.tank.TankBlockEntity;
import aztech.modern_industrialization.machines.impl.MachineBlockEntity;
import aztech.modern_industrialization.machines.impl.MachineFactory;
import badasintended.megane.api.MeganeEntrypoint;
import badasintended.megane.api.registry.*;
import badasintended.megane.impl.mixin.modern_industrialization.*;

public class ModernIndustrialization implements MeganeEntrypoint {

    private static final String[] DEP = new String[]{"modern_industrialization"};

    @Override
    public String[] dependencies() {
        return DEP;
    }

    @Override
    public void initialize() {
        EnergyTooltipRegistry.register(MachineBlockEntity.class, EnergyTooltipRegistry.Provider.of(
            t -> ((AccessorMachineBlockEntity) t).getMaxEu() > 0,
            t -> (double) ((AccessorMachineBlockEntity) t).getStoredEu(),
            t -> (double) ((AccessorMachineBlockEntity) t).getMaxEu()
        ));

        FluidTooltipRegistry.register(MachineBlockEntity.class, FluidTooltipRegistry.Provider.of(
            t -> t.getFluidStacks().size(),
            (t, i) -> t.getFluidStacks().get(i).getFluid().name,
            (t, i) -> (double) t.getFluidStacks().get(i).getAmount(),
            (t, i) -> (double) t.getFluidStacks().get(i).getCapacity()
        ));

        ProgressTooltipRegistry.register(MachineBlockEntity.class, ProgressTooltipRegistry.Provider.of(
            t -> ((AccessorMachineBlockEntity) t).getFactory().hasProgressBar(),
            t -> ((AccessorMachineBlockEntity) t).getFactory().getInputIndices(),
            t -> ((AccessorMachineBlockEntity) t).getFactory().getOutputIndices(),
            (t, i) -> t.getItemStacks().get(i).getStack(),
            t -> {
                AccessorMachineBlockEntity accessor = (AccessorMachineBlockEntity) t;
                MachineFactory factory = accessor.getFactory();
                int val = (int) ((double) accessor.getUsedEnergy() / (double) accessor.getRecipeEnergy() * 100);
                return factory.isProgressBarFlipped() ? 100 - val : val;
            }
        ));

        FluidTooltipRegistry.register(TankBlockEntity.class, FluidTooltipRegistry.Provider.of(
            t -> 1,
            (t, i) -> ((AccessorTankBlockEntity) t).getFluid().name,
            (t, i) -> (double) ((AccessorTankBlockEntity) t).getAmount(),
            (t, i) -> (double) ((AccessorTankBlockEntity) t).getCapacity()
        ));

        FluidTooltipRegistry.register(CreativeTankBlockEntity.class, FluidTooltipRegistry.Provider.of(
            t -> 1, (t, i) -> ((AccessorCreativeTankBlockEntity) t).getFluid().name, (t, i) -> -1D, (t, i) -> -1D
        ));
    }

}
