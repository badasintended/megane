package lol.bai.megane.module.create.mixin;

import java.util.Optional;

import com.simibubi.create.content.kinetics.crusher.CrushingWheelControllerBlockEntity;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import lol.bai.megane.module.create.provider.CrushingWheelControllerProvider;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(CrushingWheelControllerBlockEntity.class)
public abstract class MixinCrushingWheelControllerBlockEntity implements CrushingWheelControllerProvider.Access {

    @Unique
    private int recipeDuration;

    @SuppressWarnings({"OptionalUsedAsFieldOrParameterType", "OptionalIsPresent"})
    @Inject(method = "itemInserted", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD, remap = false)
    private void megane_storeRecipeDuration(ItemStack stack, CallbackInfo ci, Optional<ProcessingRecipe<?>> recipe) {
        recipeDuration = recipe.isPresent() ? recipe.get().getProcessingDuration() : 0;
    }

    @Override
    public int megane_recipeDuration() {
        return recipeDuration;
    }

}
