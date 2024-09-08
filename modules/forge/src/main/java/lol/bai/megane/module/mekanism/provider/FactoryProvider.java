package lol.bai.megane.module.mekanism.provider;

import lol.bai.megane.mixin.mekanism.AccessBasicInventorySlot;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.ProgressData;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.common.tile.factory.TileEntityFactory;

public class FactoryProvider implements IDataProvider<TileEntityFactory<MekanismRecipe>> {

    @Override
    public void appendData(IDataWriter data, IServerAccessor<TileEntityFactory<MekanismRecipe>> accessor, IPluginConfig config) {
        data.add(ProgressData.class, res -> {
            var factory = accessor.getTarget();
            var ratio = 0d;

            for (var i = 0; i < factory.tier.processes; i++) {
                ratio = Math.max(ratio, factory.getScaledProgress(1, i));
            }

            if (ratio > 0) {
                var progressData = ProgressData.ratio((float) ratio);
                var slots = factory.getInventorySlots(null);

                for (var slot : slots) {
                    if (slot instanceof AccessBasicInventorySlot basic) {
                        switch (basic.megane_getSlotType()) {
                            case INPUT -> progressData.input(slot.getStack());
                            case OUTPUT -> progressData.output(slot.getStack());
                        }
                    }
                }

                res.add(progressData);
            }
        });
    }

}
