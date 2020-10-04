package badasintended.megane.impl;

import badasintended.megane.api.MeganeEntrypoint;
import badasintended.megane.api.registry.FluidTooltipRegistry;
import badasintended.megane.api.registry.ProgressTooltipRegistry;
import badasintended.megane.impl.util.Arrays;
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
    public void initialize() {
        ProgressTooltipRegistry.register(GenericMachineBlockEntity.class, ProgressTooltipRegistry.Provider.of(
            b -> b.getRecipeCrafter() != null,
            b -> b.getRecipeCrafter().inputSlots == null ? Arrays.A : b.getRecipeCrafter().inputSlots,
            b -> b.getRecipeCrafter().outputSlots == null ? Arrays.A : b.getRecipeCrafter().outputSlots,
            MachineBaseBlockEntity::getStack, b -> b.getProgressScaled(100)
        ));

        ProgressTooltipRegistry.register(FusionControlComputerBlockEntity.class, ProgressTooltipRegistry.Provider.of(
            b -> Arrays.A_01, b -> Arrays.A_2, (b, i) -> b.inventory.getStack(i), b -> b.getProgressScaled(100)
        ));

        ProgressTooltipRegistry.register(ElectricFurnaceBlockEntity.class, ProgressTooltipRegistry.Provider.of(
            b -> Arrays.A_0, b -> Arrays.A_1, (b, i) -> b.inventory.getStack(i), b -> b.getProgressScaled(100)
        ));

        ProgressTooltipRegistry.register(IronAlloyFurnaceBlockEntity.class, ProgressTooltipRegistry.Provider.of(
            b -> Arrays.A_01, b -> Arrays.A_2, (b, i) -> b.inventory.getStack(i), b -> b.getProgressScaled(100)
        ));

        ProgressTooltipRegistry.register(IronFurnaceBlockEntity.class, ProgressTooltipRegistry.Provider.of(
            b -> Arrays.A_0, b -> Arrays.A_1, (b, i) -> b.inventory.getStack(i), b -> b.getProgressScaled(100)
        ));

        ProgressTooltipRegistry.register(MatterFabricatorBlockEntity.class, ProgressTooltipRegistry.Provider.of(
            b -> A_012345, b -> A_6789AB, (b, i) -> b.inventory.getStack(i), b -> b.getProgressScaled(100)
        ));

        ProgressTooltipRegistry.register(RecyclerBlockEntity.class, ProgressTooltipRegistry.Provider.of(
            b -> Arrays.A_0, b -> Arrays.A_1, (b, i) -> b.getInventory().getStack(i), b -> b.getProgress() * 10
        ));

        ProgressTooltipRegistry.register(RollingMachineBlockEntity.class, ProgressTooltipRegistry.Provider.of(
            b -> b.craftingSlots, b -> A_9, (b, i) -> b.getInventory().getStack(i), b -> b.getProgressScaled(100)
        ));

        ProgressTooltipRegistry.register(BaseFluidGeneratorBlockEntity.class, ProgressTooltipRegistry.Provider.of(
            b -> Arrays.A_01, b -> Arrays.A, (b, i) -> b.getInventory().getStack(i), b -> b.getProgressScaled(10)
        ));

        ProgressTooltipRegistry.register(SolidFuelGeneratorBlockEntity.class, ProgressTooltipRegistry.Provider.of(
            b -> Arrays.A_0, b -> Arrays.A, (b, i) -> b.getInventory().getStack(i), b -> 100 - b.getScaledBurnTime(100) == 0 ? 100 : b.getScaledBurnTime(100)
        ));

        FluidTooltipRegistry.register(MachineBaseBlockEntity.class, FluidTooltipRegistry.Provider.of(
            MachineBaseBlockEntity::showTankConfig, t -> 1,
            (t, i) -> MeganeUtils.fluidName(t.getTank().getFluid()),
            (t, i) -> (double) t.getTank().getFluidAmount().getRawValue(),
            (t, i) -> (double) t.getTank().getCapacity().getRawValue()
        ));
    }

}
