package lol.bai.megane.module.ae2.mixin;

import appeng.api.integrations.igtooltip.providers.ServerDataProvider;
import appeng.integration.modules.igtooltip.blocks.PowerStorageDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "appeng.integration.modules.wthit.WthitModule$1")
public class MixinWthitModule1 {

    @Unique
    private static final Logger megane_LOGGER = LoggerFactory.getLogger("MixinWthitModule1");

    @Inject(method = "addBlockEntityData", at = @At("HEAD"), cancellable = true, remap = false)
    private void megane_removePowerTooltip(Class<?> blockEntityClass, ServerDataProvider<?> provider, CallbackInfo ci) {
        if (provider instanceof PowerStorageDataProvider) {
            megane_LOGGER.info("[megane-applied-energistics-2] Disabled AE2's builtin power tooltip in favor of Megane's");
            ci.cancel();
        }
    }

}
