package lol.bai.megane.module.indrev.provider;

import lol.bai.megane.api.provider.ItemProvider;
import me.steven.indrev.blockentities.BaseMachineBlockEntity;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BaseMachineItemProvider extends ItemProvider<BaseMachineBlockEntity> {

    private SidedInventory inventory;

    @Override
    protected void init() {
        this.inventory = getObject().getInventory(getWorld().getBlockState(getPos()), getWorld(), getPos());
    }

    @Override
    public boolean hasItems() {
        return inventory != null;
    }

    @Override
    public int getSlotCount() {
        return inventory.size();
    }

    @Override
    public @NotNull ItemStack getStack(int slot) {
        return inventory.getStack(slot);
    }

}
