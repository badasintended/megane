package lol.bai.megane.runtime.mixin;

import mcp.mobius.waila.api.IBlockComponentProvider;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.TooltipPosition;
import mcp.mobius.waila.plugin.vanilla.provider.FurnaceProvider;
import mcp.mobius.waila.registry.Registrar;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Registrar.class, remap = false)
public class MixinRegistrar {

    @Inject(method = "addComponent(Lmcp/mobius/waila/api/IBlockComponentProvider;Lmcp/mobius/waila/api/TooltipPosition;Ljava/lang/Class;I)V", at = @At("HEAD"), cancellable = true)
    private <T> void addComponent(IBlockComponentProvider provider, TooltipPosition position, Class<T> clazz, int priority, CallbackInfo ci) {
        if (provider instanceof FurnaceProvider) {
            ci.cancel();
        }
    }

    @Inject(method = "addBlockData", at = @At("HEAD"), cancellable = true)
    private <T> void addBlockData(IDataProvider<?> provider, Class<T> clazz, int priority, CallbackInfo ci) {
        if (provider instanceof FurnaceProvider) {
            ci.cancel();
        }
    }

}
