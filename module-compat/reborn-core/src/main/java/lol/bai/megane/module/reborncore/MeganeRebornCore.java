package lol.bai.megane.module.reborncore;

import lol.bai.megane.api.MeganeModule;
import lol.bai.megane.api.registry.CommonRegistrar;
import lol.bai.megane.module.reborncore.provider.MachineBaseFluidProvider;
import lol.bai.megane.module.reborncore.provider.PowerAcceptorEnergyProvider;
import reborncore.common.blockentity.MachineBaseBlockEntity;
import reborncore.common.powerSystem.PowerAcceptorBlockEntity;

public class MeganeRebornCore implements MeganeModule {

    @Override
    public void registerCommon(CommonRegistrar registrar) {
        registrar.addEnergy(1100, PowerAcceptorBlockEntity.class, new PowerAcceptorEnergyProvider());

        registrar.addFluid(MachineBaseBlockEntity.class, new MachineBaseFluidProvider());
    }

}
