package badasintended.megane.runtime.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static badasintended.megane.runtime.util.RuntimeUtils.oldConfigVersion;
import static badasintended.megane.runtime.util.RuntimeUtils.showUpdatedConfigToast;
import static badasintended.megane.util.MeganeUtils.CONFIG_VERSION;

@Mixin(TitleScreen.class)
public class MTitleScreen {

    @Shadow
    private String splashText;

    @Inject(method = "init", at = @At("RETURN"))
    private void showConfigToast(CallbackInfo ci) {
        if (showUpdatedConfigToast && splashText != null) {
            SystemToast.show(
                MinecraftClient.getInstance().getToastManager(),
                SystemToast.Type.PACK_COPY_FAILURE,
                new TranslatableText("megane.configToast.title"),
                new TranslatableText("megane.configToast.desc", oldConfigVersion, CONFIG_VERSION)
            );
            showUpdatedConfigToast = false;
        }
    }

}
