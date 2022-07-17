package lol.bai.megane.api.provider;

import lol.bai.megane.api.provider.base.InventoryItemProvider;
import lol.bai.megane.api.registry.CommonRegistrar;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Base class for item provider.
 * <p>
 * Register implementations with {@link CommonRegistrar#addItem}
 *
 * @param <T> type of {@link BlockEntity} or {@link Entity} this provider for.
 *
 * @see InventoryItemProvider
 */
public abstract class ItemProvider<T> extends AbstractProvider<T> {

    /**
     * Returns whether the object contains any item.
     */
    public boolean hasItems() {
        return true;
    }

    /**
     * Returns the size of the storage.
     */
    public abstract int getSlotCount();

    /**
     * Returns the item of the specified slot.
     */
    @NotNull
    public abstract ItemStack getStack(int slot);

}
