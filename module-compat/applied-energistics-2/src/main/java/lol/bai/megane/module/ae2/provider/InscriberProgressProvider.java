package lol.bai.megane.module.ae2.provider;

import appeng.blockentity.misc.InscriberBlockEntity;

public class InscriberProgressProvider extends BaseProgressProvider<InscriberBlockEntity> {

    @Override
    public int getInputSlotCount() {
        return 3;
    }

    @Override
    public int getOutputSlotCount() {
        return 1;
    }

    @Override
    public int getPercentage() {
        return getObject().getProcessingTime();
    }

}
