package lol.bai.megane.module.powah;

import lol.bai.megane.api.MeganeModule;
import lol.bai.megane.api.registry.ClientRegistrar;
import lol.bai.megane.api.registry.CommonRegistrar;
import lol.bai.megane.module.powah.provider.EnergyEnergyProvider;
import lol.bai.megane.module.powah.provider.FurnatorProgressProvider;
import lol.bai.megane.module.powah.provider.InventoryItemProvider;
import lol.bai.megane.module.powah.provider.ReactorProgressProvider;
import lol.bai.megane.module.powah.provider.TankFluidProvider;
import owmii.powah.block.furnator.FurnatorTile;
import owmii.powah.block.reactor.ReactorPartTile;
import owmii.powah.lib.block.AbstractEnergyStorage;
import owmii.powah.lib.block.IInventoryHolder;
import owmii.powah.lib.block.ITankHolder;

public class MeganePowah implements MeganeModule {

    @Override
    public void registerCommon(CommonRegistrar registrar) {
        registrar.addEnergy(AbstractEnergyStorage.class, new EnergyEnergyProvider<>(AbstractEnergyStorage::getEnergy));
        registrar.addEnergy(ReactorPartTile.class, new EnergyEnergyProvider<>(it -> it.core().map(AbstractEnergyStorage::getEnergy).orElse(null)));

        registrar.addItem(IInventoryHolder.class, new InventoryItemProvider<>(IInventoryHolder::getInventory));
        registrar.addItem(ReactorPartTile.class, new InventoryItemProvider<>(it -> it.core().map(IInventoryHolder::getInventory).orElse(null)));

        registrar.addFluid(ITankHolder.class, new TankFluidProvider<>(ITankHolder::getTank));
        registrar.addFluid(ReactorPartTile.class, new TankFluidProvider<>(it -> it.core().map(ITankHolder::getTank).orElse(null)));

        registrar.addProgress(FurnatorTile.class, new FurnatorProgressProvider());
        registrar.addProgress(ReactorPartTile.class, new ReactorProgressProvider());
    }

    @Override
    public void registerClient(ClientRegistrar registrar) {
        registrar.addEnergyInfo("powah", 0x710C00, "FE");
    }

}
