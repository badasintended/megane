package lol.bai.megane.module.luggage.provider;

import com.gizmo.luggage.entity.LuggageEntity;
import lol.bai.megane.api.provider.base.InventoryItemProvider;
import net.minecraft.inventory.Inventory;
import org.jetbrains.annotations.Nullable;

public class LuggageItemProvider extends InventoryItemProvider<LuggageEntity> {

    @Override
    protected @Nullable Inventory getInventory() {
        return getObject().getInventory();
    }

}
