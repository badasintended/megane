package lol.bai.megane.runtime.mixin;

import lol.bai.megane.runtime.util.MeganeUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class MixinTitleScreen {

    @Shadow
    private String splash;

    @Inject(method = "init", at = @At("RETURN"))
    private void showConfigToast(CallbackInfo ci) {
        if (MeganeUtils.showUpdatedConfigToast && splash != null) {
            SystemToast.add(
                Minecraft.getInstance().getToasts(),
                SystemToast.SystemToastIds.PACK_COPY_FAILURE,
                Component.translatable("megane.configToast.title"),
                Component.translatable("megane.configToast.desc", MeganeUtils.oldConfigVersion, MeganeUtils.CONFIG_VERSION)
            );
            MeganeUtils.showUpdatedConfigToast = false;
        }
    }

}
