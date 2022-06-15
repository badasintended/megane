package lol.bai.megane.module.vanilla.provider;

import lol.bai.megane.api.provider.base.InventoryItemProvider;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.inventory.Inventory;
import org.jetbrains.annotations.Nullable;

public class LockableContainerItemProvider<T extends LockableContainerBlockEntity> extends InventoryItemProvider<T> {

    @Override
    public boolean blockOtherProvider() {
        return true;
    }

    @Override
    public boolean hasItems() {
        return getObject().checkUnlocked(getPlayer());
    }

    @Override
    protected @Nullable Inventory getInventory() {
        return getObject();
    }

}
