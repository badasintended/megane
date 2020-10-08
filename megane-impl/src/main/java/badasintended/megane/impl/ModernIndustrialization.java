package badasintended.megane.impl;

import aztech.modern_industrialization.blocks.creativetank.CreativeTankBlockEntity;
import aztech.modern_industrialization.blocks.tank.TankBlockEntity;
import aztech.modern_industrialization.machines.impl.MachineBlockEntity;
import aztech.modern_industrialization.machines.impl.MachineFactory;
import badasintended.megane.api.MeganeEntrypoint;
import badasintended.megane.api.provider.*;
import badasintended.megane.impl.mixin.modern_industrialization.*;

import static badasintended.megane.api.registry.TooltipRegistry.*;

public class ModernIndustrialization implements MeganeEntrypoint {

    private static final String[] DEP = new String[]{"modern_industrialization"};

    @Override
    public String[] dependencies() {
        return DEP;
    }

    @Override
    public void initialize() {
        ENERGY.register(MachineBlockEntity.class, EnergyProvider.of(
            t -> ((AccessorMachineBlockEntity) t).getMaxEu() > 0,
            t -> (double) ((AccessorMachineBlockEntity) t).getStoredEu(),
            t -> (double) ((AccessorMachineBlockEntity) t).getMaxEu()
        ));

        FLUID.register(MachineBlockEntity.class, FluidProvider.of(
            t -> t.getFluidStacks().size(),
            (t, i) -> t.getFluidStacks().get(i).getFluid().name,
            (t, i) -> (double) t.getFluidStacks().get(i).getAmount(),
            (t, i) -> (double) t.getFluidStacks().get(i).getCapacity()
        ));

        PROGRESS.register(MachineBlockEntity.class, ProgressProvider.of(
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

        FLUID.register(TankBlockEntity.class, FluidProvider.of(
            t -> 1,
            (t, i) -> ((AccessorTankBlockEntity) t).getFluid().name,
            (t, i) -> (double) ((AccessorTankBlockEntity) t).getAmount(),
            (t, i) -> (double) ((AccessorTankBlockEntity) t).getCapacity()
        ));

        FLUID.register(CreativeTankBlockEntity.class, FluidProvider.of(
            t -> 1, (t, i) -> ((AccessorCreativeTankBlockEntity) t).getFluid().name, (t, i) -> -1D, (t, i) -> -1D
        ));
    }

}
