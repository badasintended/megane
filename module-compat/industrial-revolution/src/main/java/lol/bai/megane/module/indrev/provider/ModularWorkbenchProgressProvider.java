package lol.bai.megane.module.indrev.provider;

import me.steven.indrev.blockentities.modularworkbench.ModularWorkbenchBlockEntity;
import me.steven.indrev.components.GuiSyncableComponent;

public class ModularWorkbenchProgressProvider extends AbstractMachineProgressProvider<ModularWorkbenchBlockEntity> {

    @Override
    public int getPercentage() {
        double moduleMaxProcessTime = getObject().getModuleMaxProcessTime();
        if (moduleMaxProcessTime > 0) {
            return (int) (getObject().getModuleProcessTime() / moduleMaxProcessTime * 100);
        }

        GuiSyncableComponent component = getObject().getGuiSyncableComponent();
        if (component != null) {
            double maxProcessTime = component.<Integer>get(ModularWorkbenchBlockEntity.MAX_INSTALL_TIME_ID);
            if (maxProcessTime > 0) {
                return (int) (component.<Integer>get(ModularWorkbenchBlockEntity.INSTALL_TIME_ID) / maxProcessTime * 100);
            }
        }

        return 0;
    }

}
