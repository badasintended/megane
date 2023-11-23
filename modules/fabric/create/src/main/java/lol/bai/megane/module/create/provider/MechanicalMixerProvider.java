package lol.bai.megane.module.create.provider;

import com.simibubi.create.content.kinetics.mixer.MechanicalMixerBlockEntity;
import com.simibubi.create.foundation.item.SmartInventory;
import lol.bai.megane.module.create.mixin.AccessBasinOperatingBlockEntity;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.ProgressData;

public class MechanicalMixerProvider implements IDataProvider<MechanicalMixerBlockEntity> {

    @Override
    public void appendData(IDataWriter data, IServerAccessor<MechanicalMixerBlockEntity> accessor, IPluginConfig config) {
        data.add(ProgressData.class, res -> {
            var target = (MechanicalMixerBlockEntity & Access & AccessBasinOperatingBlockEntity) accessor.getTarget();

            var recipeTicks = target.megane_getRecipeTicks();
            if (recipeTicks <= 0) return;

            var progressData = ProgressData.ratio(1 - ((float) target.processingTicks / recipeTicks));
            var basin = target.megane_getBasin();

            if (basin.isPresent()) for (SmartInventory inv : basin.get().getInvs()) {
                for (int i = 0; i < inv.getSlots(); i++) {
                    progressData.input(inv.getStackInSlot(i));
                }
            }

            res.add(progressData);
        });
    }

    public interface Access {

        int megane_getRecipeTicks();

    }

}
