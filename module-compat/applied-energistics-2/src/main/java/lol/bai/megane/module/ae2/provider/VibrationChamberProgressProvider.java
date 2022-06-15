package lol.bai.megane.module.ae2.provider;

import appeng.blockentity.misc.VibrationChamberBlockEntity;

public class VibrationChamberProgressProvider extends BaseProgressProvider<VibrationChamberBlockEntity> {

    @Override
    public int getInputSlotCount() {
        return 1;
    }

    @Override
    public int getOutputSlotCount() {
        return 0;
    }

    @Override
    public int getPercentage() {
        return 100 - (int) (getObject().getBurnTime() / getObject().getMaxBurnTime() * 100);
    }

}
