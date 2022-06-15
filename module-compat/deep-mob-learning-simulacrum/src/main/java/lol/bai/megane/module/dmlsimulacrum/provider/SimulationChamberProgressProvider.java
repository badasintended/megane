package lol.bai.megane.module.dmlsimulacrum.provider;

import io.github.projectet.dmlSimulacrum.block.entity.SimulationChamberEntity;
import lol.bai.megane.api.provider.base.SlotArrayProgressProvider;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SimulationChamberProgressProvider extends SlotArrayProgressProvider<SimulationChamberEntity> {

    private static final int[] inputSlots = {0, 1};
    private static final int[] outputSlots = {2, 3};

    @Override
    protected int[] getInputSlots() {
        return inputSlots;
    }

    @Override
    protected int[] getOutputSlots() {
        return outputSlots;
    }

    @Override
    protected @NotNull ItemStack getStack(int slot) {
        return getObject().getStack(slot);
    }

    @Override
    public int getPercentage() {
        return getObject().percentDone;
    }

}
