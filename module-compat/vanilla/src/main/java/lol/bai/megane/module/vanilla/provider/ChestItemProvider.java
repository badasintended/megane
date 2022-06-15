package lol.bai.megane.module.vanilla.provider;

import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.inventory.Inventory;
import org.jetbrains.annotations.Nullable;

public class ChestItemProvider extends LootableContainerItemProvider<ChestBlockEntity> {

    @Override
    protected @Nullable Inventory getInventory() {
        BlockState state = getObject().getCachedState();

        if (state.getBlock() instanceof ChestBlock chestBlock) {
            return ChestBlock.getInventory(chestBlock, state, getWorld(), getPos(), true);
        }

        return super.getInventory();
    }

}
