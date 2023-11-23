package lol.bai.megane.module.moderndynamics;

import dev.technici4n.moderndynamics.pipe.FluidPipeBlockEntity;
import dev.technici4n.moderndynamics.pipe.ItemPipeBlockEntity;
import lol.bai.megane.module.moderndynamics.provider.FluidPipeProvider;
import lol.bai.megane.module.moderndynamics.provider.ItemPipeProvider;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;

@SuppressWarnings("unused")
public class MeganeModernDynamics implements IWailaPlugin {

    @Override
    public void register(IRegistrar registrar) {
        registrar.addBlockData(new ItemPipeProvider(), ItemPipeBlockEntity.class);
        registrar.addBlockData(new FluidPipeProvider(), FluidPipeBlockEntity.class);
    }

}
