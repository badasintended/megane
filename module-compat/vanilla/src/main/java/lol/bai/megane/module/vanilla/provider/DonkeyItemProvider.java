package lol.bai.megane.module.vanilla.provider;

import lol.bai.megane.api.provider.base.InventoryItemProvider;
import lol.bai.megane.module.vanilla.mixin.AccessorAbstractHorseEntity;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.inventory.Inventory;

public class DonkeyItemProvider extends InventoryItemProvider<AbstractDonkeyEntity> {

    @Override
    protected Inventory getInventory() {
        AccessorAbstractHorseEntity access = getObjectCasted();
        return access.getItems();
    }

}
