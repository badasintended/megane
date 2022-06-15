package lol.bai.megane.module.vanilla.provider;

import lol.bai.megane.api.provider.base.InventoryItemProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;

public class PlayerItemProvider extends InventoryItemProvider<PlayerEntity> {

    @Override
    protected Inventory getInventory() {
        return getObject().getInventory();
    }

}
