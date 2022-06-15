package lol.bai.megane.module.alloyforgery;

import lol.bai.megane.api.MeganeModule;
import lol.bai.megane.api.registry.ClientRegistrar;
import lol.bai.megane.api.registry.CommonRegistrar;
import lol.bai.megane.module.alloyforgery.provider.ForgeControllerEnergyProvider;
import lol.bai.megane.module.alloyforgery.provider.ForgeControllerProgressProvider;
import net.minecraft.text.TranslatableText;
import wraith.alloyforgery.block.ForgeControllerBlockEntity;

@SuppressWarnings("unused")
public class MeganeAlloyForgery implements MeganeModule {

    @Override
    public void registerCommon(CommonRegistrar registrar) {
        registrar.addProgress(ForgeControllerBlockEntity.class, new ForgeControllerProgressProvider());
        registrar.addEnergy(ForgeControllerBlockEntity.class, new ForgeControllerEnergyProvider());
    }

    public void registerClient(ClientRegistrar registrar) {
        registrar.addEnergyInfo("alloy_forgery", 0xEF5252, "", new TranslatableText("megane.alloy_forgery.fuel"));
    }

}
