package badasintended.megane.runtime.mixin;

import badasintended.megane.runtime.config.screen.MeganeConfigScreen;
import mcp.mobius.waila.gui.GuiConfigPlugins;
import mcp.mobius.waila.gui.config.OptionsEntryButton;
import mcp.mobius.waila.gui.config.OptionsListWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GuiConfigPlugins.class)
public class MGuiConfigPlugins {

    @Inject(method = "getOptions", at = @At("TAIL"), remap = false)
    private void add(CallbackInfoReturnable<OptionsListWidget> cir) {
        cir.getReturnValue().children().add(0, new OptionsEntryButton("config.waila.megane", new ButtonWidget(0, 0, 100, 20, null, w ->
            MinecraftClient.getInstance().openScreen(new MeganeConfigScreen((GuiConfigPlugins) (Object) this))
        )));
    }

}
