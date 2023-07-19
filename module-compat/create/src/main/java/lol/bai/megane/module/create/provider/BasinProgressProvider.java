package lol.bai.megane.module.create.provider;

import com.simibubi.create.content.kinetics.mixer.MechanicalMixerBlockEntity;
import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.content.processing.basin.BasinOperatingBlockEntity;
import lol.bai.megane.module.create.mixin.AccessBasinTileEntity;
import org.jetbrains.annotations.Nullable;

public class BasinProgressProvider extends BaseBasinProgressProvider<BasinBlockEntity> {

    private AccessBasinTileEntity access;
    private BasinOperatingBlockEntity operator;
    private int percentage;

    @Override
    protected void init() {
        access = getObjectCasted();
        operator = access.megane_getOperator().orElse(null);

        percentage = -1;
        if (operator instanceof MechanicalMixerBlockEntity mixer) {
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
    @Nullable BasinBlockEntity getBasin() {
        return getObject();
    }

    @Override
    @Nullable BasinOperatingBlockEntity getOperator() {
        return operator;
    }

}
