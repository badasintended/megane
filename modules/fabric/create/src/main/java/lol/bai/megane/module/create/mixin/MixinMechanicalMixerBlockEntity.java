package lol.bai.megane.module.create.mixin;

import com.simibubi.create.content.kinetics.mixer.MechanicalMixerBlockEntity;
import lol.bai.megane.module.create.provider.MechanicalMixerProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MechanicalMixerBlockEntity.class)
public abstract class MixinMechanicalMixerBlockEntity implements MechanicalMixerProvider.Access {

    @Shadow
    public int processingTicks;

    @Unique
    private int recipeTicks;

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/kinetics/mixer/MechanicalMixerBlockEntity;getBasin()Ljava/util/Optional;"))
    private void megane_storeRecipeTicks(CallbackInfo ci) {
        recipeTicks = processingTicks;
    }

    @Override
    public int megane_getRecipeTicks() {
        return recipeTicks;
    }

}
