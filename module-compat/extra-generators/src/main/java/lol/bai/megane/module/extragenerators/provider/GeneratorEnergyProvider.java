package lol.bai.megane.module.extragenerators.provider;

import io.github.lucaargolo.extragenerators.common.blockentity.AbstractGeneratorBlockEntity;
import lol.bai.megane.api.provider.EnergyProvider;

@SuppressWarnings("rawtypes")
public class GeneratorEnergyProvider extends EnergyProvider<AbstractGeneratorBlockEntity> {

    @Override
    public long getStored() {
        return getObject().getEnergyStorage().getAmount();
    }

    @Override
    public long getMax() {
        return getObject().getEnergyStorage().getCapacity();
    }

}
