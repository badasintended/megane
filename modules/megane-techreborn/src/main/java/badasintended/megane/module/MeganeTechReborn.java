package badasintended.megane.module;

import badasintended.megane.MeganeUtils;
import badasintended.megane.api.MeganeApi;
import badasintended.megane.api.registry.FluidTooltipRegistry;
import badasintended.megane.api.registry.ProgressTooltipRegistry;
import reborncore.common.blockentity.MachineBaseBlockEntity;
import techreborn.blockentity.generator.BaseFluidGeneratorBlockEntity;
import techreborn.blockentity.machine.GenericMachineBlockEntity;
import techreborn.blockentity.machine.misc.DrainBlockEntity;
import techreborn.blockentity.machine.multiblock.FluidReplicatorBlockEntity;
import techreborn.blockentity.machine.multiblock.IndustrialGrinderBlockEntity;
import techreborn.blockentity.machine.multiblock.IndustrialSawmillBlockEntity;
import techreborn.blockentity.storage.fluid.TankUnitBaseBlockEntity;

public class MeganeTechReborn implements MeganeApi {

    private static void registerFluid(Class<? extends MachineBaseBlockEntity> clazz) {
        FluidTooltipRegistry.register(
            clazz, b -> 1,
            (b, i) -> MeganeUtils.fluidName(b.getTank().getFluid()),
            (b, i) -> b.getTank().getFluidAmount().getRawValue() / 1000D,
            (b, i) -> b.getTank().getCapacity().getRawValue() / 1000D
        );
    }

    @Override
    public void initialize() {
        registerFluid(BaseFluidGeneratorBlockEntity.class);
        registerFluid(DrainBlockEntity.class);
        registerFluid(FluidReplicatorBlockEntity.class);
        registerFluid(IndustrialGrinderBlockEntity.class);
        registerFluid(IndustrialSawmillBlockEntity.class);
        registerFluid(TankUnitBaseBlockEntity.class);

        ProgressTooltipRegistry.register(
            GenericMachineBlockEntity.class,
            b -> b.getRecipeCrafter().inputs,
            b -> b.getRecipeCrafter().outputs,
            (b, i) -> b.getStack(b.getRecipeCrafter().inputSlots[i]),
            (b, i) -> b.getStack(b.getRecipeCrafter().outputSlots[i]),
            b -> b.getProgressScaled(100)
        );
    }

}
