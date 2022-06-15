package lol.bai.megane.module.ae2;

import appeng.blockentity.AEBaseInvBlockEntity;
import appeng.blockentity.misc.InscriberBlockEntity;
import appeng.blockentity.misc.VibrationChamberBlockEntity;
import appeng.blockentity.networking.EnergyCellBlockEntity;
import lol.bai.megane.api.MeganeModule;
import lol.bai.megane.api.registry.ClientRegistrar;
import lol.bai.megane.api.registry.CommonRegistrar;
import lol.bai.megane.module.ae2.provider.BaseItemProvider;
import lol.bai.megane.module.ae2.provider.InscriberProgressProvider;
import lol.bai.megane.module.ae2.provider.PowerStorageEnergyProvider;
import lol.bai.megane.module.ae2.provider.VibrationChamberProgressProvider;

public class MeganeAppliedEnergistics2 implements MeganeModule {

    @Override
    public void registerCommon(CommonRegistrar registrar) {
        //registrar.addEnergy(IAEPowerStorage.class, new PowerStorageEnergyProvider<>());
        //registrar.addEnergy(EnergyAcceptorBlockEntity.class, new EnergyAcceptorEnergyProvider());
        registrar.addEnergy(EnergyCellBlockEntity.class, new PowerStorageEnergyProvider<>());

        registrar.addItem(AEBaseInvBlockEntity.class, new BaseItemProvider());

        registrar.addProgress(InscriberBlockEntity.class, new InscriberProgressProvider());
        registrar.addProgress(VibrationChamberBlockEntity.class, new VibrationChamberProgressProvider());
    }

    @Override
    public void registerClient(ClientRegistrar registrar) {
        registrar.addEnergyInfo("ae2", 0x64099F, "AE");
    }

}
