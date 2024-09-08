package lol.bai.megane.module.indrev.provider;

import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.ProgressData;
import me.steven.indrev.blockentities.modularworkbench.ModularWorkbenchBlockEntity;
import me.steven.indrev.components.GuiSyncableComponent;

public class ModularWorkbenchProvider implements IDataProvider<ModularWorkbenchBlockEntity> {

    @Override
    public void appendData(IDataWriter data, IServerAccessor<ModularWorkbenchBlockEntity> accessor, IPluginConfig config) {
        data.add(ProgressData.class, res -> {
            var workbench = accessor.getTarget();

            float moduleMaxProcessTime = workbench.getModuleMaxProcessTime();
            if (moduleMaxProcessTime > 0) {
                res.add(ProgressData.ratio(workbench.getModuleProcessTime() / moduleMaxProcessTime));
                return;
            }

            GuiSyncableComponent component = workbench.getGuiSyncableComponent();
            if (component != null) {
                float maxProcessTime = component.<Integer>get(ModularWorkbenchBlockEntity.MAX_INSTALL_TIME_ID);
                if (maxProcessTime > 0) {
                    res.add(ProgressData.ratio(component.<Integer>get(ModularWorkbenchBlockEntity.INSTALL_TIME_ID) / maxProcessTime));
                }
            }
        });
    }

}
