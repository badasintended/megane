package badasintended.megane.impl;

import badasintended.megane.api.MeganeEntrypoint;
import badasintended.megane.api.registry.FluidTooltipRegistry;
import badasintended.megane.api.registry.ProgressTooltipRegistry;
import badasintended.megane.util.MeganeUtils;
import reborncore.common.blockentity.MachineBaseBlockEntity;
import techreborn.blockentity.generator.BaseFluidGeneratorBlockEntity;
import techreborn.blockentity.generator.basic.SolidFuelGeneratorBlockEntity;
import techreborn.blockentity.machine.GenericMachineBlockEntity;
import techreborn.blockentity.machine.iron.IronAlloyFurnaceBlockEntity;
import techreborn.blockentity.machine.iron.IronFurnaceBlockEntity;
import techreborn.blockentity.machine.misc.DrainBlockEntity;
import techreborn.blockentity.machine.multiblock.*;
import techreborn.blockentity.machine.tier1.*;
import techreborn.blockentity.machine.tier3.MatterFabricatorBlockEntity;
import techreborn.blockentity.storage.fluid.TankUnitBaseBlockEntity;

public class MeganeTechReborn implements MeganeEntrypoint {

    private static final int[] A = new int[0];
    private static final int[] A_01 = new int[]{0, 1};
    private static final int[] A_2 = new int[]{2};
    private static final int[] A_0 = new int[]{0};
    private static final int[] A_1 = new int[]{1};
    private static final int[] A_012345 = new int[]{0, 1, 2, 3, 4, 5};
    private static final int[] A_6789AB = new int[]{6, 7, 8, 9, 10, 11};
    private static final int[] A_9 = new int[]{9};

    private static final String[] DEP = new String[]{"techreborn"};

    @Override
    public String[] dependencies() {
        return DEP;
    }

    @SuppressWarnings({"ConstantConditions", "deprecation"})
    private void fluid(Class<? extends MachineBaseBlockEntity> clazz) {
        FluidTooltipRegistry.register(clazz, FluidTooltipRegistry.Provider.of(
            t -> 1,
            (t, i) -> MeganeUtils.fluidName(t.getTank().getFluid()),
            (t, i) -> (double) t.getTank().getFluidAmount().getRawValue(),
            (t, i) -> (double) t.getTank().getCapacity().getRawValue()
        ));
    }

    @Override
    public void initialize() {
        ProgressTooltipRegistry.register(GenericMachineBlockEntity.class, ProgressTooltipRegistry.Provider.of(
            b -> b.getRecipeCrafter() == null ? A : b.getRecipeCrafter().inputSlots == null ? A : b.getRecipeCrafter().inputSlots,
            b -> b.getRecipeCrafter() == null ? A : b.getRecipeCrafter().outputSlots == null ? A : b.getRecipeCrafter().outputSlots,
            MachineBaseBlockEntity::getStack, b -> b.getProgressScaled(100)
        ));

        ProgressTooltipRegistry.register(FusionControlComputerBlockEntity.class, ProgressTooltipRegistry.Provider.of(
            b -> A_01, b -> A_2, (b, i) -> b.inventory.getStack(i), b -> b.getProgressScaled(100)
        ));

        ProgressTooltipRegistry.register(ElectricFurnaceBlockEntity.class, ProgressTooltipRegistry.Provider.of(
            b -> A_0, b -> A_1, (b, i) -> b.inventory.getStack(i), b -> b.getProgressScaled(100)
        ));

        ProgressTooltipRegistry.register(IronAlloyFurnaceBlockEntity.class, ProgressTooltipRegistry.Provider.of(
            b -> A_01, b -> A_2, (b, i) -> b.inventory.getStack(i), b -> b.getProgressScaled(100)
        ));

        ProgressTooltipRegistry.register(IronFurnaceBlockEntity.class, ProgressTooltipRegistry.Provider.of(
            b -> A_0, b -> A_1, (b, i) -> b.inventory.getStack(i), b -> b.getProgressScaled(100)
        ));

        ProgressTooltipRegistry.register(MatterFabricatorBlockEntity.class, ProgressTooltipRegistry.Provider.of(
            b -> A_012345, b -> A_6789AB, (b, i) -> b.inventory.getStack(i), b -> b.getProgressScaled(100)
        ));

        ProgressTooltipRegistry.register(RecyclerBlockEntity.class, ProgressTooltipRegistry.Provider.of(
            b -> A_0, b -> A_1, (b, i) -> b.getInventory().getStack(i), b -> b.getProgress() * 10
        ));

        ProgressTooltipRegistry.register(RollingMachineBlockEntity.class, ProgressTooltipRegistry.Provider.of(
            b -> b.craftingSlots, b -> A_9, (b, i) -> b.getInventory().getStack(i), b -> b.getProgressScaled(100)
        ));

        ProgressTooltipRegistry.register(BaseFluidGeneratorBlockEntity.class, ProgressTooltipRegistry.Provider.of(
            b -> A_01, b -> A, (b, i) -> b.getInventory().getStack(i), b -> b.getProgressScaled(10)
        ));

        ProgressTooltipRegistry.register(SolidFuelGeneratorBlockEntity.class, ProgressTooltipRegistry.Provider.of(
            b -> A_0, b -> A, (b, i) -> b.getInventory().getStack(i), b -> 100 - b.getScaledBurnTime(100) == 0 ? 100 : b.getScaledBurnTime(100)
        ));

        fluid(FluidReplicatorBlockEntity.class);
        fluid(IndustrialGrinderBlockEntity.class);
        fluid(IndustrialSawmillBlockEntity.class);
        fluid(BaseFluidGeneratorBlockEntity.class);
        fluid(DrainBlockEntity.class);
        fluid(TankUnitBaseBlockEntity.class);
    }

}
