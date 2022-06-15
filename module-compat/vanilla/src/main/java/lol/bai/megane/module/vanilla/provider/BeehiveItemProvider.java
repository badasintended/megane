package lol.bai.megane.module.vanilla.provider;

import java.util.Map;
import java.util.WeakHashMap;

import lol.bai.megane.api.provider.ItemProvider;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.jetbrains.annotations.NotNull;

public class BeehiveItemProvider extends ItemProvider<BeehiveBlockEntity> {

    private final Map<PlayerEntity, ItemStack> itemStackCache = new WeakHashMap<>();

    @Override
    public int getSlotCount() {
        return 1;
    }

    @Override
    public @NotNull ItemStack getStack(int slot) {
        ItemStack stack = itemStackCache.computeIfAbsent(getPlayer(), p -> new ItemStack(Items.HONEYCOMB));
        stack.setCount(getObject().getBeeCount());
        return stack;
    }

}
