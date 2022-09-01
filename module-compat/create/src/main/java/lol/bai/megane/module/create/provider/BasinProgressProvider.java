package lol.bai.megane.module.create.provider;

import com.simibubi.create.content.contraptions.components.mixer.MechanicalMixerTileEntity;
import com.simibubi.create.content.contraptions.processing.BasinOperatingTileEntity;
import com.simibubi.create.content.contraptions.processing.BasinTileEntity;
import lol.bai.megane.module.create.mixin.AccessBasinTileEntity;
import org.jetbrains.annotations.Nullable;

public class BasinProgressProvider extends BaseBasinProgressProvider<BasinTileEntity> {

    private AccessBasinTileEntity access;
    private BasinOperatingTileEntity operator;
    private int percentage;

    @Override
    protected void init() {
        access = getObjectCasted();
        operator = access.megane_getOperator().orElse(null);

        percentage = -1;
        if (operator instanceof MechanicalMixerTileEntity mixer) {
            percentage = MechanicalMixerProgressProvider.getPercentage(mixer);
        }

        super.init();
    }

    @Override
    public boolean hasProgress() {
        return percentage > -1;
    }

    @Override
    public int getPercentage() {
        return percentage;
    }

    @Override
    @Nullable BasinTileEntity getBasin() {
        return getObject();
    }

    @Override
    @Nullable BasinOperatingTileEntity getOperator() {
        return operator;
    }

}
