package lol.bai.megane.module.indrev.provider;

import lol.bai.megane.api.provider.EnergyProvider;
import me.steven.indrev.IREnergyStorage;
import me.steven.indrev.api.machines.Tier;
import me.steven.indrev.blockentities.MachineBlockEntity;

@SuppressWarnings("rawtypes")
public class MachineEnergyProvider extends EnergyProvider<MachineBlockEntity> {

    private IREnergyStorage energyStorage;

    @Override
    protected void init() {
        this.energyStorage = getObject().getStorage();
    }

    @Override
    public long getStored() {
        return isCreative() ? -1L : energyStorage.getAmount();
    }

    @Override
    public long getMax() {
        return isCreative() ? -1L : energyStorage.getCapacity();
    }

    private boolean isCreative() {
        return getObject().getTier() == Tier.CREATIVE;
    }

}
