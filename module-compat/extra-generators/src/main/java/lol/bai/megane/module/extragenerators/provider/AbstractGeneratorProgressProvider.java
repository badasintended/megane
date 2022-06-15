package lol.bai.megane.module.extragenerators.provider;

import java.util.function.Function;

import io.github.lucaargolo.extragenerators.common.blockentity.AbstractGeneratorBlockEntity;
import io.github.lucaargolo.extragenerators.utils.SimpleSidedInventory;
import lol.bai.megane.api.provider.base.SlotArrayProgressProvider;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("rawtypes")
public abstract class AbstractGeneratorProgressProvider<T extends AbstractGeneratorBlockEntity> extends SlotArrayProgressProvider<T> {

    private static final int[] OUTPUT_SLOTS = new int[0];

    private SimpleSidedInventory inventory;
    private final int[] inputSlots;
    private final Function<T, SimpleSidedInventory> inventoryGetter;

    protected AbstractGeneratorProgressProvider(Function<T, SimpleSidedInventory> inventoryGetter, int... inputSlots) {
        this.inputSlots = inputSlots;
        this.inventoryGetter = inventoryGetter;
    }

    abstract protected int getCurrentBurnTime();

    abstract protected int getBurnTime();

    @Override
    protected void init() {
        this.inventory = inventoryGetter.apply(getObject());
    }

    @Override
    protected int[] getInputSlots() {
        return inputSlots;
    }

    @Override
    protected int[] getOutputSlots() {
        return OUTPUT_SLOTS;
    }

    @Override
    protected @NotNull ItemStack getStack(int slot) {
        return inventory.getStack(slot);
    }

    @Override
    public int getPercentage() {
        return remainingPercentage(getCurrentBurnTime(), getBurnTime());
    }

}
