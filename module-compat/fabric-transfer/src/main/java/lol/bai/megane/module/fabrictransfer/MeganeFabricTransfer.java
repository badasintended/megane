package lol.bai.megane.module.fabrictransfer;

import lol.bai.megane.api.MeganeModule;
import lol.bai.megane.api.registry.ClientRegistrar;
import lol.bai.megane.api.registry.CommonRegistrar;
import lol.bai.megane.module.fabrictransfer.provider.FabricCauldronProvider;
import lol.bai.megane.module.fabrictransfer.provider.FabricFluidInfoProvider;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;

public class MeganeFabricTransfer implements MeganeModule {

    @Override
    public void registerCommon(CommonRegistrar registrar) {
        registrar.addCauldron(Block.class, new FabricCauldronProvider());
    }

    @Override
    public void registerClient(ClientRegistrar registrar) {
        registrar.addFluidInfo(Fluid.class, new FabricFluidInfoProvider(), 900);
    }

}
