package lol.bai.megane.module.ae2.provider;

import appeng.blockentity.misc.VibrationChamberBlockEntity;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.ProgressData;

public class VibrationChamberProvider implements IDataProvider<VibrationChamberBlockEntity> {

    @Override
    public void appendData(IDataWriter data, IServerAccessor<VibrationChamberBlockEntity> accessor, IPluginConfig config) {
        data.add(ProgressData.class, res -> {
            var target = accessor.getTarget();

            res.add(ProgressData.ratio(1 - (float) (target.getRemainingFuelTicks() / target.getFuelItemFuelTicks()))
                .input(target.getInternalInventory().getStackInSlot(0)));
        });
    }

}
