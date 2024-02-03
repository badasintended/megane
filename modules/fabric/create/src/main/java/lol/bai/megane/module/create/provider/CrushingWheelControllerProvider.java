package lol.bai.megane.module.create.provider;

import com.simibubi.create.content.kinetics.crusher.CrushingWheelControllerBlockEntity;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.ProgressData;

public class CrushingWheelControllerProvider implements IDataProvider<CrushingWheelControllerBlockEntity> {

    @Override
    public void appendData(IDataWriter data, IServerAccessor<CrushingWheelControllerBlockEntity> accessor, IPluginConfig config) {
        data.add(ProgressData.class, res -> {
            var target = (CrushingWheelControllerBlockEntity & Access) accessor.getTarget();
            var recipeDuration = target.megane_recipeDuration();
            if (recipeDuration <= 0) return;

            var inventory = target.inventory;
            if (inventory.remainingTime <= 0) return;

            var progressData = ProgressData.ratio(1 - (inventory.remainingTime / recipeDuration));

            progressData.ensureInputSpace(inventory.getSlotCount());
            for (int i = 0; i < inventory.getSlotCount(); i++) {
                progressData.input(inventory.getStackInSlot(i));
            }

            res.add(progressData);
        });
    }

    public interface Access {

        int megane_recipeDuration();

    }

}
