package lol.bai.megane.module.wirelessnetworks;

import lol.bai.megane.module.wirelessnetworks.provider.NetworkNodeProvider;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import me.steven.wirelessnetworks.blockentity.NetworkNodeBlockEntity;

@SuppressWarnings("unused")
public class MeganeWirelessNetworks implements IWailaPlugin {

    @Override
    public void register(IRegistrar registrar) {
        registrar.addBlockData(new NetworkNodeProvider(), NetworkNodeBlockEntity.class);
    }

}
