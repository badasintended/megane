package badasintended.megane.runtime.mixin;

import mcp.mobius.waila.api.IBlockComponentProvider;
import mcp.mobius.waila.api.IServerDataProvider;
import mcp.mobius.waila.api.TooltipPosition;
import mcp.mobius.waila.plugin.vanilla.FurnaceComponent;
import mcp.mobius.waila.registry.TooltipRegistrar;
import net.minecraft.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = TooltipRegistrar.class, remap = false)
public class MixinTooltipRegistrar {

    @Inject(method = "addComponent(Lmcp/mobius/waila/api/IBlockComponentProvider;Lmcp/mobius/waila/api/TooltipPosition;Ljava/lang/Class;I)V", at = @At("HEAD"), cancellable = true)
    private <T> void addComponent(IBlockComponentProvider provider, TooltipPosition position, Class<T> clazz, int priority, CallbackInfo ci) {
        if (provider instanceof FurnaceComponent) {
            ci.cancel();
        }
    }

    @Inject(method = "addBlockData", at = @At("HEAD"), cancellable = true)
    private <T> void addBlockData(IServerDataProvider<BlockEntity> provider, Class<T> clazz, CallbackInfo ci) {
        if (provider instanceof FurnaceComponent) {
            ci.cancel();
        }
    }

}
