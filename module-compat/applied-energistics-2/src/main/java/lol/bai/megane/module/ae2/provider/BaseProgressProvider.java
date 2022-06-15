package lol.bai.megane.module.ae2.provider;

import appeng.blockentity.AEBaseInvBlockEntity;
import lol.bai.megane.api.provider.ProgressProvider;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public abstract class BaseProgressProvider<T extends AEBaseInvBlockEntity> extends ProgressProvider<T> {

    @Override
    public @NotNull ItemStack getInputStack(int slot) {
        return getObject().getInternalInventory().getStackInSlot(slot);
    }

    @Override
    public @NotNull ItemStack getOutputStack(int slot) {
        return getObject().getInternalInventory().getStackInSlot(slot + getInputSlotCount());
    }

}
