package lol.bai.megane.module.powah.provider;

import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.EnergyData;
import mcp.mobius.waila.api.data.FluidData;
import mcp.mobius.waila.api.data.ItemData;
import mcp.mobius.waila.api.data.ProgressData;
import owmii.powah.block.reactor.ReactorPartTile;
import owmii.powah.block.reactor.ReactorTile;

public class ReactorPartProvider implements IDataProvider<ReactorPartTile> {

    @Override
    public void appendData(IDataWriter data, IServerAccessor<ReactorPartTile> accessor, IPluginConfig config) {
        data.add(EnergyData.class, res -> {
            var energy = accessor.getTarget().core().map(ReactorTile::getEnergy).orElse(null);
            if (energy == null) return;

            res.add(EnergyData.of(energy.getStored(), energy.getCapacity()));
        });

        data.add(ItemData.class, res -> {
            var inventory = accessor.getTarget().core().map(ReactorTile::getInventory).orElse(null);
            if (inventory == null) return;

            res.add(ItemData.of(config).add(inventory.getStacks()));
        });

        data.add(FluidData.class, res -> {
            var tank = accessor.getTarget().core().map(ReactorTile::getTank).orElse(null);
            if (tank == null) return;

            TankHolderProvider.addTank(res, tank);
        });

        data.add(ProgressData.class, res -> {
            var core = accessor.getTarget().core().orElse(null);
            if (core == null) return;

            res.add(ProgressData.ratio(core.fuel.subSized()));
        });
    }

}
