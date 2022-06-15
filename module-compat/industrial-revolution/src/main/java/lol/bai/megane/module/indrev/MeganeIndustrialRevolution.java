package lol.bai.megane.module.indrev;

import lol.bai.megane.api.MeganeModule;
import lol.bai.megane.api.registry.ClientRegistrar;
import lol.bai.megane.api.registry.CommonRegistrar;
import lol.bai.megane.module.indrev.provider.BaseFluidInfoProvider;
import lol.bai.megane.module.indrev.provider.BaseMachineItemProvider;
import lol.bai.megane.module.indrev.provider.CraftingMachineProgressProvider;
import lol.bai.megane.module.indrev.provider.FluidComponentHolderFluidProvider;
import lol.bai.megane.module.indrev.provider.MachineEnergyProvider;
import lol.bai.megane.module.indrev.provider.ModularWorkbenchProgressProvider;
import me.steven.indrev.blockentities.BaseMachineBlockEntity;
import me.steven.indrev.blockentities.MachineBlockEntity;
import me.steven.indrev.blockentities.crafters.CraftingMachineBlockEntity;
import me.steven.indrev.blockentities.modularworkbench.ModularWorkbenchBlockEntity;
import me.steven.indrev.blockentities.storage.TankBlockEntity;
import me.steven.indrev.fluids.BaseFluid;

@SuppressWarnings("unused")
public class MeganeIndustrialRevolution implements MeganeModule {

    @Override
    public void registerCommon(CommonRegistrar registrar) {
        registrar.addEnergy(MachineBlockEntity.class, new MachineEnergyProvider());

        registrar.addItem(BaseMachineBlockEntity.class, new BaseMachineItemProvider());

        registrar.addFluid(MachineBlockEntity.class, new FluidComponentHolderFluidProvider<>(MachineBlockEntity::getFluidComponent));
        registrar.addFluid(TankBlockEntity.class, new FluidComponentHolderFluidProvider<>(TankBlockEntity::getFluidComponent));

        registrar.addProgress(CraftingMachineBlockEntity.class, new CraftingMachineProgressProvider());
        registrar.addProgress(ModularWorkbenchBlockEntity.class, new ModularWorkbenchProgressProvider());
    }

    @Override
    public void registerClient(ClientRegistrar registrar) {
        registrar.addFluidInfo(BaseFluid.class, new BaseFluidInfoProvider());

        registrar.addEnergyInfo("indrev", 0x3B4ADE, "LF");
    }

}
