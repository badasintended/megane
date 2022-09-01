package lol.bai.megane.module.create.provider;

import com.simibubi.create.content.contraptions.components.millstone.MillingRecipe;
import com.simibubi.create.content.contraptions.components.millstone.MillstoneTileEntity;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler;
import lol.bai.megane.module.create.mixin.AccessMillstoneProgressProvider;

public class MillstoneProgressProvider extends ItemStackHandlerProgressProvider<MillstoneTileEntity> {

    AccessMillstoneProgressProvider access;
    MillingRecipe recipe;

    @Override
    protected void init() {
        super.init();
        access = getObjectCasted();
        recipe = access.megane_lastRecipe();
    }

    @Override
    public boolean hasProgress() {
        return recipe != null && getObject().timer > 0;
    }

    @Override
    ItemStackHandler getInputStackHandler() {
        return getObject().inputInv;
    }

    @Override
    ItemStackHandler getOutputStackHandler() {
        return getObject().outputInv;
    }

    @Override
    public int getPercentage() {
        return remainingPercentage(getObject().timer, recipe.getProcessingDuration());
    }

}
