package lol.bai.megane.module.reborncore;

import lol.bai.megane.module.reborncore.provider.MachineBaseProvider;
import lol.bai.megane.module.reborncore.provider.PowerAcceptorProvider;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import reborncore.common.blockentity.MachineBaseBlockEntity;
import reborncore.common.powerSystem.PowerAcceptorBlockEntity;

public class MeganeRebornCore implements IWailaPlugin {

    @Override
    public void register(IRegistrar registrar) {
        registrar.addBlockData(new MachineBaseProvider(), MachineBaseBlockEntity.class);
        registrar.addBlockData(new PowerAcceptorProvider(), PowerAcceptorBlockEntity.class, 1100);
    }

}
