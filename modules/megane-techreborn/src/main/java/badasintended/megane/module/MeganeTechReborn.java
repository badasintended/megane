package badasintended.megane.module;

import badasintended.megane.api.MeganeApi;
import badasintended.megane.api.registry.FluidTooltipRegistry;
import reborncore.common.blockentity.MachineBaseBlockEntity;
import reborncore.common.fluid.FluidUtil;
import techreborn.blockentity.generator.BaseFluidGeneratorBlockEntity;
import techreborn.blockentity.machine.misc.DrainBlockEntity;
import techreborn.blockentity.machine.multiblock.FluidReplicatorBlockEntity;
import techreborn.blockentity.machine.multiblock.IndustrialGrinderBlockEntity;
import techreborn.blockentity.machine.multiblock.IndustrialSawmillBlockEntity;
import techreborn.blockentity.storage.fluid.TankUnitBaseBlockEntity;

public class MeganeTechReborn implements MeganeApi {

    private static void registerFluid(Class<? extends MachineBaseBlockEntity> clazz) {
        FluidTooltipRegistry.register(
            clazz, t -> 1,
            (t, i) -> FluidUtil.getFluidName(t.getTank().getFluid()),
            (t, i) -> t.getTank().getFluidAmount().getRawValue() / 1000D,
            (t, i) -> t.getTank().getCapacity().getRawValue() / 1000D
        );
    }

    @Override
    public void onMeganeInitialize() {
        registerFluid(BaseFluidGeneratorBlockEntity.class);
        registerFluid(DrainBlockEntity.class);
        registerFluid(FluidReplicatorBlockEntity.class);
        registerFluid(IndustrialGrinderBlockEntity.class);
        registerFluid(IndustrialSawmillBlockEntity.class);
        registerFluid(TankUnitBaseBlockEntity.class);
    }

}
