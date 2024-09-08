package lol.bai.megane.module.techreborn;

import lol.bai.megane.module.techreborn.provider.GenericMachineProvider;
import lol.bai.megane.module.techreborn.provider.HardCodedProgressProvider;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.data.EnergyData;
import techreborn.TechReborn;
import techreborn.blockentity.generator.BaseFluidGeneratorBlockEntity;
import techreborn.blockentity.generator.basic.SolidFuelGeneratorBlockEntity;
import techreborn.blockentity.machine.GenericMachineBlockEntity;
import techreborn.blockentity.machine.iron.IronAlloyFurnaceBlockEntity;
import techreborn.blockentity.machine.iron.IronFurnaceBlockEntity;
import techreborn.blockentity.machine.multiblock.FusionControlComputerBlockEntity;
import techreborn.blockentity.machine.tier1.ElectricFurnaceBlockEntity;
import techreborn.blockentity.machine.tier1.RollingMachineBlockEntity;
import techreborn.blockentity.machine.tier3.MatterFabricatorBlockEntity;

public class MeganeTechReborn implements IWailaPlugin {

    @Override
    public void register(IRegistrar registrar) {
        EnergyData.describe(TechReborn.MOD_ID).color(0x800800).unit("E");

        registrar.addBlockData(new GenericMachineProvider(), GenericMachineBlockEntity.class, 1050);

        registrar.addBlockData(HardCodedProgressProvider
            .builder(FusionControlComputerBlockEntity::getProgressScaled)
            .input(0, 1).output(2)
            .build(), FusionControlComputerBlockEntity.class);

        registrar.addBlockData(HardCodedProgressProvider
            .builder(ElectricFurnaceBlockEntity::getProgressScaled)
            .input(0).output(1)
            .build(), ElectricFurnaceBlockEntity.class);

        registrar.addBlockData(HardCodedProgressProvider
            .builder(MatterFabricatorBlockEntity::getProgressScaled)
            .input(0, 1, 2, 3, 4, 5).output(6, 7, 8, 9, 10, 11)
            .build(), MatterFabricatorBlockEntity.class);

        registrar.addBlockData(HardCodedProgressProvider
            .builder(RollingMachineBlockEntity::getProgressScaled)
            .input(0, 1, 2, 3, 4, 5, 6, 7, 8)
            .build(), RollingMachineBlockEntity.class);

        registrar.addBlockData(HardCodedProgressProvider
            .builder(BaseFluidGeneratorBlockEntity::getProgressScaled).scale(10)
            .input(0, 1)
            .build(), BaseFluidGeneratorBlockEntity.class);

        registrar.addBlockData(HardCodedProgressProvider
            .builder(IronAlloyFurnaceBlockEntity::getProgressScaled)
            .input(0, 1).output(2)
            .build(), IronAlloyFurnaceBlockEntity.class);

        registrar.addBlockData(HardCodedProgressProvider
            .builder(IronFurnaceBlockEntity::getProgressScaled)
            .input(0).output(1)
            .build(), IronFurnaceBlockEntity.class);

        registrar.addBlockData(HardCodedProgressProvider
            .builder(SolidFuelGeneratorBlockEntity::getScaledBurnTime).inverted()
            .input(0)
            .build(), SolidFuelGeneratorBlockEntity.class);
    }

}
