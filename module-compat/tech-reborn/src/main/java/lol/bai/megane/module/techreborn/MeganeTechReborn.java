package lol.bai.megane.module.techreborn;

import lol.bai.megane.api.MeganeModule;
import lol.bai.megane.api.registry.ClientRegistrar;
import lol.bai.megane.api.registry.CommonRegistrar;
import lol.bai.megane.module.techreborn.provider.GenericMachineProgressProvider;
import lol.bai.megane.module.techreborn.provider.HardCodedProgressProvider;
import lol.bai.megane.module.techreborn.provider.ModFluidInfoProvider;
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

public class MeganeTechReborn implements MeganeModule {

    @Override
    public void registerCommon(CommonRegistrar registrar) {
        registrar.addProgress(GenericMachineBlockEntity.class, new GenericMachineProgressProvider());

        registrar.addProgress(FusionControlComputerBlockEntity.class, HardCodedProgressProvider
            .builder(FusionControlComputerBlockEntity::getProgressScaled)
            .input(0, 1).output(2)
            .build());

        registrar.addProgress(ElectricFurnaceBlockEntity.class, HardCodedProgressProvider
            .builder(ElectricFurnaceBlockEntity::getProgressScaled)
            .input(0).output(1)
            .build());

        registrar.addProgress(MatterFabricatorBlockEntity.class, HardCodedProgressProvider
            .builder(MatterFabricatorBlockEntity::getProgressScaled)
            .input(0, 1, 2, 3, 4, 5).output(6, 7, 8, 9, 10, 11)
            .build());

        registrar.addProgress(RollingMachineBlockEntity.class, HardCodedProgressProvider
            .builder(RollingMachineBlockEntity::getProgressScaled)
            .input(0, 1, 2, 3, 4, 5, 6, 7, 8)
            .build());

        registrar.addProgress(BaseFluidGeneratorBlockEntity.class, HardCodedProgressProvider
            .builder(BaseFluidGeneratorBlockEntity::getProgressScaled).scale(10)
            .input(0, 1)
            .build());

        registrar.addProgress(IronAlloyFurnaceBlockEntity.class, HardCodedProgressProvider
            .builder(IronAlloyFurnaceBlockEntity::getProgressScaled)
            .input(0, 1).output(2)
            .build());

        registrar.addProgress(IronFurnaceBlockEntity.class, HardCodedProgressProvider
            .builder(IronFurnaceBlockEntity::getProgressScaled)
            .input(0).output(1)
            .build());

        registrar.addProgress(SolidFuelGeneratorBlockEntity.class, HardCodedProgressProvider
            .builder(SolidFuelGeneratorBlockEntity::getScaledBurnTime).inverted()
            .input(0)
            .build());
    }

    @Override
    public void registerClient(ClientRegistrar registrar) {
        new ModFluidInfoProvider(registrar).registerAll();

        registrar.addEnergyInfo(TechReborn.MOD_ID, 0x800800, "E");
    }

}
