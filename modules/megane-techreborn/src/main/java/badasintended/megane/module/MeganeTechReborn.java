package badasintended.megane.module;

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
import techreborn.blockentity.machine.multiblock.FluidReplicatorBlockEntity;
import techreborn.blockentity.machine.multiblock.FusionControlComputerBlockEntity;
import techreborn.blockentity.machine.multiblock.IndustrialGrinderBlockEntity;
import techreborn.blockentity.machine.multiblock.IndustrialSawmillBlockEntity;
import techreborn.blockentity.machine.tier1.ElectricFurnaceBlockEntity;
import techreborn.blockentity.machine.tier1.RecyclerBlockEntity;
import techreborn.blockentity.machine.tier1.RollingMachineBlockEntity;
import techreborn.blockentity.machine.tier3.MatterFabricatorBlockEntity;
import techreborn.blockentity.storage.fluid.TankUnitBaseBlockEntity;

public class MeganeTechReborn implements MeganeEntrypoint {

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
        final int[] empty = new int[0];
        final int[] i2o1_I = new int[]{0, 1};
        final int[] i2o1_O = new int[]{2};
        final int[] i1o1_I = new int[]{0};
        final int[] i1o1_O = new int[]{1};
        final int[] matterFabricatorI = new int[]{0, 1, 2, 3, 4, 5};
        final int[] matterFabricatorO = new int[]{6, 7, 8, 9, 10, 11};
        final int[] rollingMachineO = new int[]{9};

        ProgressTooltipRegistry.register(GenericMachineBlockEntity.class, ProgressTooltipRegistry.Provider.of(
            b -> b.getRecipeCrafter() == null ? empty : b.getRecipeCrafter().inputSlots == null ? empty : b.getRecipeCrafter().inputSlots,
            b -> b.getRecipeCrafter() == null ? empty : b.getRecipeCrafter().outputSlots == null ? empty : b.getRecipeCrafter().outputSlots,
            MachineBaseBlockEntity::getStack, b -> b.getProgressScaled(100)
        ));

        ProgressTooltipRegistry.register(FusionControlComputerBlockEntity.class, ProgressTooltipRegistry.Provider.of(
            b -> i2o1_I, b -> i2o1_O, (b, i) -> b.inventory.getStack(i), b -> b.getProgressScaled(100)
        ));

        ProgressTooltipRegistry.register(ElectricFurnaceBlockEntity.class, ProgressTooltipRegistry.Provider.of(
            b -> i1o1_I, b -> i1o1_O, (b, i) -> b.inventory.getStack(i), b -> b.getProgressScaled(100)
        ));

        ProgressTooltipRegistry.register(IronAlloyFurnaceBlockEntity.class, ProgressTooltipRegistry.Provider.of(
            b -> i2o1_I, b -> i2o1_O, (b, i) -> b.inventory.getStack(i), b -> b.getProgressScaled(100)
        ));

        ProgressTooltipRegistry.register(IronFurnaceBlockEntity.class, ProgressTooltipRegistry.Provider.of(
            b -> i1o1_I, b -> i1o1_O, (b, i) -> b.inventory.getStack(i), b -> b.getProgressScaled(100)
        ));

        ProgressTooltipRegistry.register(MatterFabricatorBlockEntity.class, ProgressTooltipRegistry.Provider.of(
            b -> matterFabricatorI, b -> matterFabricatorO, (b, i) -> b.inventory.getStack(i), b -> b.getProgressScaled(100)
        ));

        ProgressTooltipRegistry.register(RecyclerBlockEntity.class, ProgressTooltipRegistry.Provider.of(
            b -> i1o1_I, b -> i1o1_O, (b, i) -> b.getInventory().getStack(i), b -> b.getProgress() * 10
        ));

        ProgressTooltipRegistry.register(RollingMachineBlockEntity.class, ProgressTooltipRegistry.Provider.of(
            b -> b.craftingSlots, b -> rollingMachineO, (b, i) -> b.getInventory().getStack(i), b -> b.getProgressScaled(100)
        ));

        ProgressTooltipRegistry.register(BaseFluidGeneratorBlockEntity.class, ProgressTooltipRegistry.Provider.of(
            b -> i2o1_I, b -> empty, (b, i) -> b.getInventory().getStack(i), b -> b.getProgressScaled(10)
        ));

        ProgressTooltipRegistry.register(SolidFuelGeneratorBlockEntity.class, ProgressTooltipRegistry.Provider.of(
            b -> i1o1_I, b -> empty, (b, i) -> b.getInventory().getStack(i), b -> 100 - b.getScaledBurnTime(100) == 0 ? 100 : b.getScaledBurnTime(100)
        ));

        fluid(FluidReplicatorBlockEntity.class);
        fluid(IndustrialGrinderBlockEntity.class);
        fluid(IndustrialSawmillBlockEntity.class);
        fluid(BaseFluidGeneratorBlockEntity.class);
        fluid(DrainBlockEntity.class);
        fluid(TankUnitBaseBlockEntity.class);
    }

}
