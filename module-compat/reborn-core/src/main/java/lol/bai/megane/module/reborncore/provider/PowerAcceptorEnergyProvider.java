package lol.bai.megane.module.reborncore.provider;

import lol.bai.megane.api.provider.EnergyProvider;
import reborncore.common.powerSystem.PowerAcceptorBlockEntity;

public class PowerAcceptorEnergyProvider extends EnergyProvider<PowerAcceptorBlockEntity> {

    @Override
    public long getStored() {
        return getObject().getEnergy();
    }

    @Override
    public long getMax() {
        return getObject().getMaxStoredPower();
    }

}
