package lol.bai.megane.module.vanilla.provider;

import lol.bai.megane.api.provider.base.SlotArrayProgressProvider;
import lol.bai.megane.module.vanilla.mixin.AccessorAbstractFurnaceBlockEntity;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class FurnaceProgressProvider extends SlotArrayProgressProvider<AbstractFurnaceBlockEntity> {

    private static final int[] INPUT_SLOTS = {0, 1};
    private static final int[] OUTPUT_SLOTS = {2};

    @Override
    protected int[] getInputSlots() {
        return INPUT_SLOTS;
    }

    @Override
    protected int[] getOutputSlots() {
        return OUTPUT_SLOTS;
    }

    @Override
    protected @NotNull ItemStack getStack(int slot) {
        return getObject().getStack(slot);
    }

    @Override
    public int getPercentage() {
        AccessorAbstractFurnaceBlockEntity access = getObjectCasted();
        return currentPercentage(access.getCookTime(), access.getCookTimeTotal());
    }

}
