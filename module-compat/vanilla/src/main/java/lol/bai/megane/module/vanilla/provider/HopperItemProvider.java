package lol.bai.megane.module.vanilla.provider;

import lol.bai.megane.api.provider.base.InventoryItemProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.inventory.Inventory;
import org.jetbrains.annotations.Nullable;

public class HopperItemProvider extends InventoryItemProvider<BlockEntity> {

    @Override
    protected @Nullable Inventory getInventory() {
        return HopperBlockEntity.getInventoryAt(getWorld(), getPos());
    }

}
