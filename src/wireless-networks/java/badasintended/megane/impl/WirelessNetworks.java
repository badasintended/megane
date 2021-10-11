package badasintended.megane.impl;

import badasintended.megane.api.MeganeModule;
import badasintended.megane.api.provider.EnergyProvider;
import badasintended.megane.api.registry.MeganeRegistrar;
import me.steven.wirelessnetworks.blockentity.NetworkNodeBlockEntity;
import me.steven.wirelessnetworks.network.Network;

public class WirelessNetworks implements MeganeModule {

    @Override
    public void register(MeganeRegistrar registrar) {
        registrar.energy(NetworkNodeBlockEntity.class, new EnergyProvider<>() {
            Network network;

            @Override
            public boolean hasEnergy(NetworkNodeBlockEntity networkNodeBlockEntity) {
                network = networkNodeBlockEntity.getNetwork().orElse(null);
                return network != null;
            }

            @Override
            public double getStored(NetworkNodeBlockEntity networkNodeBlockEntity) {
                return network.getAmount();
            }

            @Override
            public double getMax(NetworkNodeBlockEntity networkNodeBlockEntity) {
                return network.getCapacity();
            }
        });
    }

}
