package lol.bai.megane.module.indrev.provider;

import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.EnergyData;
import mcp.mobius.waila.api.data.ItemData;
import me.steven.indrev.api.machines.Tier;
import me.steven.indrev.blockentities.MachineBlockEntity;

public class MachineProvider implements IDataProvider<MachineBlockEntity<?>> {

    @Override
    public void appendData(IDataWriter data, IServerAccessor<MachineBlockEntity<?>> accessor, IPluginConfig config) {
        var machine = accessor.getTarget();

        data.add(EnergyData.class, res -> {
            if (machine.getTier() == Tier.CREATIVE) {
                res.add(EnergyData.INFINITE);
            } else {
                var storage = machine.getStorage();
                res.add(EnergyData.of(storage.getAmount(), storage.getCapacity()));
            }
        });

        data.add(ItemData.class, res -> {
            var component = machine.getInventoryComponent();
            if (component == null) return;

            var inventory = component.getInventory();
            res.add(ItemData.of(config).vanilla(inventory));
        });
    }

}
