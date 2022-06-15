package lol.bai.megane.api.provider;

import lol.bai.megane.api.provider.base.InventoryItemProvider;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * @see InventoryItemProvider
 */
public abstract class ItemProvider<T> extends AbstractProvider<T> {

    public boolean hasItems() {
        return true;
    }

    public abstract int getSlotCount();

    @NotNull
    public abstract ItemStack getStack(int slot);

}
