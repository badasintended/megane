package lol.bai.megane.module.ae2.mixin;

import java.util.List;

import appeng.integration.modules.waila.BaseDataProvider;
import appeng.integration.modules.waila.BlockEntityDataProvider;
import appeng.integration.modules.waila.tile.PowerStorageDataProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntityDataProvider.class)
public class MixinBlockEntityDataProvider {

    @Mutable
    @Final
    @Shadow
    private List<BaseDataProvider> providers;

    /**
     * Remove AE2's own energy tooltip, since megane provides it
     */
    @Inject(method = "<init>", at = @At("TAIL"), remap = false)
    private void removeEnergyTooltip(CallbackInfo ci) {
        providers = providers.stream().filter(it -> !(it instanceof PowerStorageDataProvider)).toList();
    }

}
