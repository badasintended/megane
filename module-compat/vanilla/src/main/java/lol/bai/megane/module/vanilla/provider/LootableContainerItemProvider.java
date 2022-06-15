package lol.bai.megane.module.vanilla.provider;

import lol.bai.megane.module.vanilla.mixin.AccessorLootableContainerBlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;

public class LootableContainerItemProvider<T extends LootableContainerBlockEntity> extends LockableContainerItemProvider<T> {

    @Override
    public boolean hasItems() {
        AccessorLootableContainerBlockEntity access = getObjectCasted();
        return access.getLootTableId() == null;
    }

}
