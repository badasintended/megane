package lol.bai.megane.module.techreborn.provider;

import reborncore.common.recipes.RecipeCrafter;
import techreborn.blockentity.machine.GenericMachineBlockEntity;

public class GenericMachineProgressProvider extends AbstractInventoryProgressProvider<GenericMachineBlockEntity> {

    private RecipeCrafter crafter;

    @Override
    protected void init() {
        this.crafter = getObject().getRecipeCrafter();
    }

    @Override
    public boolean hasProgress() {
        return crafter != null;
    }

    @Override
    protected int[] getInputSlots() {
        return crafter.inputSlots;
    }

    @Override
    protected int[] getOutputSlots() {
        return crafter.outputSlots;
    }

    @Override
    public int getPercentage() {
        return getObject().getProgressScaled(100);
    }

}
