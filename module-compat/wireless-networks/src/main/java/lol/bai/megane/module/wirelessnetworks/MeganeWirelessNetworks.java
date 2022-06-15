package lol.bai.megane.module.wirelessnetworks;

import lol.bai.megane.api.MeganeModule;
import lol.bai.megane.api.registry.CommonRegistrar;
import lol.bai.megane.module.wirelessnetworks.provider.NetworkNodeEnergyProvider;
import me.steven.wirelessnetworks.blockentity.NetworkNodeBlockEntity;

@SuppressWarnings("unused")
public class MeganeWirelessNetworks implements MeganeModule {

    @Override
    public void registerCommon(CommonRegistrar registrar) {
        registrar.addEnergy(NetworkNodeBlockEntity.class, new NetworkNodeEnergyProvider());
    }

}
