package lol.bai.megane.module.moderndynamics;

import dev.technici4n.moderndynamics.pipe.FluidPipeBlockEntity;
import dev.technici4n.moderndynamics.pipe.ItemPipeBlockEntity;
import lol.bai.megane.api.MeganeModule;
import lol.bai.megane.api.registry.CommonRegistrar;
import lol.bai.megane.module.moderndynamics.provider.PipeFluidProvider;
import lol.bai.megane.module.moderndynamics.provider.PipeItemProvider;

@SuppressWarnings("unused")
public class MeganeModernDynamics implements MeganeModule {

    @Override
    public void registerCommon(CommonRegistrar registrar) {
        registrar.addFluid(FluidPipeBlockEntity.class, new PipeFluidProvider());
        registrar.addItem(ItemPipeBlockEntity.class, new PipeItemProvider());
    }

}
