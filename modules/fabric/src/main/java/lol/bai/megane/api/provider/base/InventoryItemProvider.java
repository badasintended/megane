package lol.bai.megane.api.provider.base;

import javax.swing.text.html.parser.Entity;

import lol.bai.megane.api.provider.ItemProvider;
import lol.bai.megane.api.registry.CommonRegistrar;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Base class for {@link Container} based item provider.
 * <p>
 * Register implementations with {@link CommonRegistrar#addItem}
 *
 * @param <T> type of {@link BlockEntity} or {@link Entity} this provider for.
 *
 * @deprecated use WTHIT API
 */
@Deprecated
public abstract class InventoryItemProvider<T> extends ItemProvider<T> {

    private Container inventory;

    /**
     * Returns the {@link Container instance} of the object.
     */
    @Nullable
    protected abstract Container getInventory();

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
        return inventory.getContainerSize();
    }

    @NotNull
    @Override
    public ItemStack getStack(int slot) {
        return inventory.getItem(slot);
    }

}
