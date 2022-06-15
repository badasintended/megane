package lol.bai.megane.module.wirelessnetworks.provider;

import lol.bai.megane.api.provider.EnergyProvider;
import me.steven.wirelessnetworks.blockentity.NetworkNodeBlockEntity;
import me.steven.wirelessnetworks.network.Network;

public class NetworkNodeEnergyProvider extends EnergyProvider<NetworkNodeBlockEntity> {

    private Network network;

    @Override
    protected void init() {
        this.network = getObject().getNetwork().orElse(null);
    }

    @Override
    public boolean hasEnergy() {
        return network != null;
    }

    @Override
    public long getStored() {
        return network.getAmount();
    }

    @Override
    public long getMax() {
        return network.getCapacity();
    }

}
