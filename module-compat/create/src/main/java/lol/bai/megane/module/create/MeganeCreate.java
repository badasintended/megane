package lol.bai.megane.module.create;

import com.simibubi.create.content.contraptions.fluids.tank.CreativeFluidTankTileEntity;
import com.simibubi.create.content.contraptions.fluids.tank.FluidTankTileEntity;
import com.tterrag.registrate.fabric.SimpleFlowableFluid;
import lol.bai.megane.api.MeganeModule;
import lol.bai.megane.api.registry.ClientRegistrar;
import lol.bai.megane.api.registry.CommonRegistrar;
import lol.bai.megane.module.create.provider.CreativeFluidTankFluidProvider;
import lol.bai.megane.module.create.provider.FluidTankFluidProvider;
import lol.bai.megane.module.create.provider.SimpleFlowableFluidInfoProvider;

@SuppressWarnings("unused")
public class MeganeCreate implements MeganeModule {

    @Override
    public void registerCommon(CommonRegistrar registrar) {
        registrar.addFluid(CreativeFluidTankTileEntity.class, new CreativeFluidTankFluidProvider());
        registrar.addFluid(FluidTankTileEntity.class, new FluidTankFluidProvider<>());
    }

    @Override
    public void registerClient(ClientRegistrar registrar) {
        registrar.addFluidInfo(SimpleFlowableFluid.class, new SimpleFlowableFluidInfoProvider());
    }

}
