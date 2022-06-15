package lol.bai.megane.module.test.provider;

import lol.bai.megane.api.provider.EnergyProvider;
import net.minecraft.block.entity.ChestBlockEntity;

public class TestEnergyProvider<T extends ChestBlockEntity> extends EnergyProvider<T> {

    @Override
    public long getStored() {
        return 30000;
    }

    @Override
    public long getMax() {
        return 100000;
    }

}
