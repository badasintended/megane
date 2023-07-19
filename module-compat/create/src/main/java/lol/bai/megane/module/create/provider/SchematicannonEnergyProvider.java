package lol.bai.megane.module.create.provider;

import com.simibubi.create.content.schematics.cannon.SchematicannonBlockEntity;
import lol.bai.megane.api.provider.EnergyProvider;

public class SchematicannonEnergyProvider extends EnergyProvider<SchematicannonBlockEntity> {

    @Override
    public long getStored() {
        return (long) (getObject().fuelLevel * 100);
    }

    @Override
    public long getMax() {
        return 100;
    }

}
