package lol.bai.megane.module.create.mixin;

import java.util.Optional;

import com.simibubi.create.content.contraptions.components.crusher.CrushingWheelControllerTileEntity;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipe;
import io.github.fabricators_of_create.porting_lib.transfer.item.RecipeWrapper;
import lol.bai.megane.module.create.provider.CrushingWheelControllerProgressProvider;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(CrushingWheelControllerTileEntity.class)
public abstract class MixinCrushingWheelControllerTileEntity implements CrushingWheelControllerProgressProvider.Access {

    @Unique
    private int recipeDuration;

    @SuppressWarnings({"OptionalUsedAsFieldOrParameterType", "OptionalIsPresent"})
    @Inject(method = "itemInserted", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void megane_storeRecipeDuration(ItemStack stack, CallbackInfo ci, Optional<ProcessingRecipe<RecipeWrapper>> recipe) {
        recipeDuration = recipe.isPresent() ? recipe.get().getProcessingDuration() : 0;
    }

    @Override
    public int megane_recipeDuration() {
        return recipeDuration;
    }

}
