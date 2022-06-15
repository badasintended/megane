package lol.bai.megane.module.indrev.provider;

import me.steven.indrev.blockentities.crafters.CraftingMachineBlockEntity;
import me.steven.indrev.components.CraftingComponent;

@SuppressWarnings("rawtypes")
public class CraftingMachineProgressProvider extends AbstractMachineProgressProvider<CraftingMachineBlockEntity> {

    @Override
    public int getPercentage() {
        int result = 0;

        for (CraftingComponent<?> component : getObject().getCraftingComponents()) {
            double current = component.getProcessTime();
            double max = component.getTotalProcessTime();
            result = Math.max(result, (int) (current / max * 100));
        }

        return result;
    }

}
