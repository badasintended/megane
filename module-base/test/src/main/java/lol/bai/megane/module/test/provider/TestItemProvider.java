package lol.bai.megane.module.test.provider;

import lol.bai.megane.api.provider.ItemProvider;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import org.jetbrains.annotations.NotNull;

public class TestItemProvider extends ItemProvider<ChestBlockEntity> {

    private static final ItemStack NOT_RANDOM_STACK = new ItemStack(Items.DIAMOND, 42);

    @Override
    public int getSlotCount() {
        return 13;
    }

    @Override
    public @NotNull ItemStack getStack(int slot) {
        return slot == 0 ? NOT_RANDOM_STACK : new ItemStack(Registry.ITEM.getRandom(getWorld().random).map(RegistryEntry::value).orElse(Items.ACACIA_BOAT));
    }

}
