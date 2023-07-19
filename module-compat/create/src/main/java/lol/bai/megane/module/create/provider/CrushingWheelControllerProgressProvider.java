package lol.bai.megane.module.create.provider;

import com.simibubi.create.content.kinetics.crusher.CrushingWheelControllerBlockEntity;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class CrushingWheelControllerProgressProvider extends ItemStackHandlerProgressProvider<CrushingWheelControllerBlockEntity> {

    Access access;
    int recipeDuration;

    @Override
    protected void init() {
        super.init();
        access = getObjectCasted();
        recipeDuration = access.megane_recipeDuration();
    }

    @Override
    public boolean hasProgress() {
        return recipeDuration > 0 && getObject().inventory.remainingTime > 0;
    }

    @Override
    @Nullable ItemStackHandler getInputStackHandler() {
        return getObject().inventory;
    }

    @Override
    @Nullable ItemStackHandler getOutputStackHandler() {
        return null;
    }

    @Override
    public int getPercentage() {
        return remainingPercentage(getObject().inventory.remainingTime, recipeDuration);
    }

    public interface Access {

        int megane_recipeDuration();

    }

}
