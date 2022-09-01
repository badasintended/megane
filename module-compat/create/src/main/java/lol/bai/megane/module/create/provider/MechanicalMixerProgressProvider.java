package lol.bai.megane.module.create.provider;

import com.simibubi.create.content.contraptions.components.mixer.MechanicalMixerTileEntity;
import com.simibubi.create.content.contraptions.processing.BasinOperatingTileEntity;
import org.jetbrains.annotations.Nullable;

public class MechanicalMixerProgressProvider extends BasinOperatingProgressProvider<MechanicalMixerTileEntity> {

    static int getPercentage(MechanicalMixerTileEntity mixer) {
        if (mixer.processingTicks < 0) {
            return -1;
        }

        int recipeTicks = ((Access) mixer).megane_getRecipeTicks();
        return recipeTicks > 0 ? remainingPercentage(mixer.processingTicks, recipeTicks) : -1;
    }

    private int percentage;

    @Override
    protected void init() {
        percentage = getPercentage(getObject());
        super.init();
    }

    @Override
    public boolean hasProgress() {
        return percentage >= 0;
    }

    @Override
    public int getPercentage() {
        return percentage;
    }

    @Override
    @Nullable BasinOperatingTileEntity getOperator() {
        return getObject();
    }

    public interface Access {

        int megane_getRecipeTicks();

    }

}
