package lol.bai.megane.module.alloyforgery.provider;

import lol.bai.megane.api.provider.EnergyProvider;
import lol.bai.megane.module.alloyforgery.mixin.AccessorForgeControllerBlockEntity;
import wraith.alloyforgery.block.ForgeControllerBlockEntity;

public class ForgeControllerEnergyProvider extends EnergyProvider<ForgeControllerBlockEntity> {

    private AccessorForgeControllerBlockEntity access;

    @Override
    protected void init() {
        access = getObjectCasted();
    }

    @Override
    public long getStored() {
        return Math.round(access.getFuel());
    }

    @Override
    public long getMax() {
        return access.getForgeDefinition().fuelCapacity();
    }

}
