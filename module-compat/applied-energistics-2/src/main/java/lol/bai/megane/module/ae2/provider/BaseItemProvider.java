package lol.bai.megane.module.ae2.provider;

import appeng.blockentity.AEBaseInvBlockEntity;
import lol.bai.megane.api.provider.ItemProvider;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BaseItemProvider extends ItemProvider<AEBaseInvBlockEntity> {

    @Override
    public int getSlotCount() {
        return getObject().getInternalInventory().size();
    }

    @Override
    public @NotNull ItemStack getStack(int slot) {
        return getObject().getInternalInventory().getStackInSlot(slot);
    }

}
