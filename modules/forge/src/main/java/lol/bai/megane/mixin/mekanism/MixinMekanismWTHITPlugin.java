package lol.bai.megane.mixin.mekanism;

import mcp.mobius.waila.api.IRegistrar;
import mekanism.common.integration.lookingat.wthit.MekanismWTHITPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MekanismWTHITPlugin.class)
public class MixinMekanismWTHITPlugin {

    @Unique
    private static final Logger megane_LOGGER = LoggerFactory.getLogger("MixinMekanismWTHITPlugin");

    @Inject(method = "register", at = @At("HEAD"), remap = false, cancellable = true)
    private void megane_disable(IRegistrar registration, CallbackInfo ci) {
        megane_LOGGER.info("[megane-mekanism] Disabled Mekanism's builtin WTHIT compatibility in favor of Megane's");
        ci.cancel();
    }

}
