package lol.bai.megane.module.indrev.provider;

import lol.bai.megane.api.provider.ProgressProvider;
import me.steven.indrev.blockentities.MachineBlockEntity;
import me.steven.indrev.components.InventoryComponent;
import me.steven.indrev.inventories.IRInventory;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("rawtypes")
public abstract class AbstractMachineProgressProvider<T extends MachineBlockEntity> extends ProgressProvider<T> {

    private IRInventory inventory;

    @Override
    protected void init() {
        InventoryComponent component = getObject().getInventoryComponent();
        this.inventory = component == null ? null : component.getInventory();
    }

    @Override
    public boolean hasProgress() {
        return inventory != null;
    }

    @Override
    public int getInputSlotCount() {
        return inventory.getInputSlots().length;
    }

    @Override
    public int getOutputSlotCount() {
        return inventory.getOutputSlots().length;
    }

    @Override
    public @NotNull ItemStack getInputStack(int slot) {
        return inventory.getStack(inventory.getInputSlots()[slot]);
    }

    @Override
    public @NotNull ItemStack getOutputStack(int slot) {
        return inventory.getStack(inventory.getOutputSlots()[slot]);
    }

}
