package lol.bai.megane.module.vanilla.provider;

import lol.bai.megane.api.provider.base.InventoryItemProvider;
import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.inventory.Inventory;

public class EnderChestItemProvider extends InventoryItemProvider<EnderChestBlockEntity> {

    @Override
    protected Inventory getInventory() {
        return getPlayer().getEnderChestInventory();
    }

}
