package badasintended.megane.runtime.mixin;

import badasintended.megane.runtime.config.screen.MeganeConfigScreen;
import mcp.mobius.waila.gui.screen.PluginConfigScreen;
import mcp.mobius.waila.gui.widget.ButtonEntry;
import mcp.mobius.waila.gui.widget.ConfigListWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.LiteralText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PluginConfigScreen.class)
public class MixinPluginConfigScreen {

    @Inject(method = "getOptions", at = @At("TAIL"), remap = false)
    private void add(CallbackInfoReturnable<ConfigListWidget> cir) {
        cir.getReturnValue().children().add(1, new ButtonEntry("config.waila.megane", new ButtonWidget(0, 0, 100, 20, LiteralText.EMPTY, w ->
            MinecraftClient.getInstance().openScreen(new MeganeConfigScreen((PluginConfigScreen) (Object) this))
        )));
    }

}
