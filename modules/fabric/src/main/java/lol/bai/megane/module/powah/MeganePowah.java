package lol.bai.megane.module.powah;

import lol.bai.megane.module.powah.provider.EnergyStorageProvider;
import lol.bai.megane.module.powah.provider.FurnatorProvider;
import lol.bai.megane.module.powah.provider.InventoryHolderProvider;
import lol.bai.megane.module.powah.provider.ReactorPartProvider;
import lol.bai.megane.module.powah.provider.TankHolderProvider;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.data.EnergyData;
import owmii.powah.block.furnator.FurnatorTile;
import owmii.powah.block.reactor.ReactorPartTile;
import owmii.powah.lib.block.AbstractEnergyStorage;
import owmii.powah.lib.block.IInventoryHolder;
import owmii.powah.lib.block.ITankHolder;

public class MeganePowah implements IWailaPlugin {

    @Override
    public void register(IRegistrar registrar) {
        EnergyData.describe("powah").color(0x710C00).unit("FE");

        registrar.addBlockData(new EnergyStorageProvider(), AbstractEnergyStorage.class);
        registrar.addBlockData(new InventoryHolderProvider(), IInventoryHolder.class);
        registrar.addBlockData(new TankHolderProvider(), ITankHolder.class);

        registrar.addBlockData(new FurnatorProvider(), FurnatorTile.class);
        registrar.addBlockData(new ReactorPartProvider(), ReactorPartTile.class);
    }

}
