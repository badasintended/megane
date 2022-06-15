package lol.bai.megane.module.alloyforgery.provider;

import lol.bai.megane.api.provider.base.SlotArrayProgressProvider;
import lol.bai.megane.module.alloyforgery.mixin.AccessorForgeControllerBlockEntity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import wraith.alloyforgery.block.ForgeControllerBlockEntity;

public class ForgeControllerProgressProvider extends SlotArrayProgressProvider<ForgeControllerBlockEntity> {

    private static final int[] INPUT = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 11};
    private static final int[] OUTPUT = {10};

    private AccessorForgeControllerBlockEntity access;

    @Override
    protected void init() {
        access = getObjectCasted();
    }

    @Override
    protected int[] getInputSlots() {
        return INPUT;
    }

    @Override
    protected int[] getOutputSlots() {
        return OUTPUT;
    }

    @Override
    protected @NotNull ItemStack getStack(int slot) {
        return getObject().getStack(slot);
    }

    @Override
    public int getPercentage() {
        return currentPercentage(getObject().getCurrentSmeltTime(), access.getForgeDefinition().maxSmeltTime());
    }

}
