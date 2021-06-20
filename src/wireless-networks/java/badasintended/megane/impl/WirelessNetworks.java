package badasintended.megane.impl;

import badasintended.megane.api.MeganeModule;
import badasintended.megane.api.provider.EnergyProvider;
import badasintended.megane.api.registry.MeganeRegistrar;
import dev.technici4n.fasttransferlib.api.energy.EnergyIo;
import me.steven.wirelessnetworks.blockentity.NetworkNodeBlockEntity;

public class WirelessNetworks implements MeganeModule {

    @Override
    public void register(MeganeRegistrar registrar) {
        registrar.energy(NetworkNodeBlockEntity.class, new EnergyProvider<>() {
            EnergyIo io;

            @Override
            public boolean hasEnergy(NetworkNodeBlockEntity networkNodeBlockEntity) {
                io = networkNodeBlockEntity.getNetwork().orElse(null);
                return io != null;
            }

            @Override
            public double getStored(NetworkNodeBlockEntity networkNodeBlockEntity) {
                return io.getEnergy();
            }

            @Override
            public double getMax(NetworkNodeBlockEntity networkNodeBlockEntity) {
                return io.getEnergyCapacity();
            }
        });
    }

}
