package lol.bai.megane.api.provider.base;

import javax.swing.text.html.parser.Entity;

import lol.bai.megane.api.provider.ItemProvider;
import lol.bai.megane.api.registry.CommonRegistrar;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Base class for {@link Inventory} based item provider.
 * <p>
 * Register implementations with {@link CommonRegistrar#addItem}
 *
 * @param <T> type of {@link BlockEntity} or {@link Entity} this provider for.
 */
public abstract class InventoryItemProvider<T> extends ItemProvider<T> {

    private Inventory inventory;

    /**
     * Returns the {@link Inventory instance} of the object.
     */
    @Nullable
    protected abstract Inventory getInventory();

    @Override
    protected void init() {
        this.inventory = getInventory();
    }

    @Override
    public boolean hasItems() {
        return inventory != null;
    }

    @Override
    public int getSlotCount() {
        return inventory.size();
    }

    @NotNull
    @Override
    public ItemStack getStack(int slot) {
        return inventory.getStack(slot);
    }

}
