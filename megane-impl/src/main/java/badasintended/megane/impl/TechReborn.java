package badasintended.megane.impl;

import badasintended.megane.api.MeganeEntrypoint;
import badasintended.megane.api.provider.FluidProvider;
import badasintended.megane.api.provider.ProgressProvider;
import badasintended.megane.impl.util.A;
import badasintended.megane.util.MeganeUtils;
import reborncore.common.blockentity.MachineBaseBlockEntity;
import techreborn.blockentity.generator.BaseFluidGeneratorBlockEntity;
import techreborn.blockentity.generator.basic.SolidFuelGeneratorBlockEntity;
import techreborn.blockentity.machine.GenericMachineBlockEntity;
import techreborn.blockentity.machine.iron.IronAlloyFurnaceBlockEntity;
import techreborn.blockentity.machine.iron.IronFurnaceBlockEntity;
import techreborn.blockentity.machine.multiblock.FusionControlComputerBlockEntity;
import techreborn.blockentity.machine.tier1.*;
import techreborn.blockentity.machine.tier3.MatterFabricatorBlockEntity;

import static badasintended.megane.api.registry.TooltipRegistry.FLUID;
import static badasintended.megane.api.registry.TooltipRegistry.PROGRESS;

public class TechReborn implements MeganeEntrypoint {

    private static final int[] A_012345 = new int[]{0, 1, 2, 3, 4, 5};
    private static final int[] A_6789AB = new int[]{6, 7, 8, 9, 10, 11};
    private static final int[] A_9 = new int[]{9};

    private static final String[] DEP = new String[]{"techreborn"};

    @Override
    public String[] dependencies() {
        return DEP;
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void initialize() {
        PROGRESS.register(GenericMachineBlockEntity.class, ProgressProvider.of(
            b -> b.getRecipeCrafter() != null,
            b -> b.getRecipeCrafter().inputSlots == null ? A.A : b.getRecipeCrafter().inputSlots,
            b -> b.getRecipeCrafter().outputSlots == null ? A.A : b.getRecipeCrafter().outputSlots,
            MachineBaseBlockEntity::getStack, b -> b.getProgressScaled(100)
        ));

        PROGRESS.register(FusionControlComputerBlockEntity.class, ProgressProvider.of(
            b -> A.A_01, b -> A.A_2, (b, i) -> b.inventory.getStack(i), b -> b.getProgressScaled(100)
        ));

        PROGRESS.register(ElectricFurnaceBlockEntity.class, ProgressProvider.of(
            b -> A.A_0, b -> A.A_1, (b, i) -> b.inventory.getStack(i), b -> b.getProgressScaled(100)
        ));

        PROGRESS.register(IronAlloyFurnaceBlockEntity.class, ProgressProvider.of(
            b -> A.A_01, b -> A.A_2, (b, i) -> b.inventory.getStack(i), b -> b.getProgressScaled(100)
        ));

        PROGRESS.register(IronFurnaceBlockEntity.class, ProgressProvider.of(
            b -> A.A_0, b -> A.A_1, (b, i) -> b.inventory.getStack(i), b -> b.getProgressScaled(100)
        ));

        PROGRESS.register(MatterFabricatorBlockEntity.class, ProgressProvider.of(
            b -> A_012345, b -> A_6789AB, (b, i) -> b.inventory.getStack(i), b -> b.getProgressScaled(100)
        ));

        PROGRESS.register(RecyclerBlockEntity.class, ProgressProvider.of(
            b -> A.A_0, b -> A.A_1, (b, i) -> b.getInventory().getStack(i), b -> b.getProgress() * 10
        ));

        PROGRESS.register(RollingMachineBlockEntity.class, ProgressProvider.of(
            b -> b.craftingSlots, b -> A_9, (b, i) -> b.getInventory().getStack(i), b -> b.getProgressScaled(100)
        ));

        PROGRESS.register(BaseFluidGeneratorBlockEntity.class, ProgressProvider.of(
            b -> A.A_01, b -> A.A, (b, i) -> b.getInventory().getStack(i), b -> b.getProgressScaled(10)
        ));

        PROGRESS.register(SolidFuelGeneratorBlockEntity.class, ProgressProvider.of(
            b -> A.A_0, b -> A.A, (b, i) -> b.getInventory().getStack(i), b -> 100 - b.getScaledBurnTime(100) == 0 ? 100 : b.getScaledBurnTime(100)
        ));

        FLUID.register(MachineBaseBlockEntity.class, FluidProvider.of(
            MachineBaseBlockEntity::showTankConfig, t -> 1,
            (t, i) -> MeganeUtils.fluidName(t.getTank().getFluid()),
            (t, i) -> (double) t.getTank().getFluidAmount().getRawValue(),
            (t, i) -> (double) t.getTank().getCapacity().getRawValue()
        ));
    }

}
