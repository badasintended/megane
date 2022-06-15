package lol.bai.megane.module.ae2.provider;

import appeng.blockentity.networking.EnergyAcceptorBlockEntity;

public class EnergyAcceptorEnergyProvider extends PowerStorageEnergyProvider<EnergyAcceptorBlockEntity> {

    @Override
    public boolean hasEnergy() {
        return false;
    }

    @Override
    public boolean blockOtherProvider() {
        return true;
    }

}
