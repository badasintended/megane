package lol.bai.megane.module.test.provider;

import lol.bai.megane.api.provider.base.SlotArrayProgressProvider;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import org.jetbrains.annotations.NotNull;

public class TestProgressProvider extends SlotArrayProgressProvider<ChestBlockEntity> {

    private static final int[] INPUT = {0, 1, 2};
    private static final int[] OUTPUT = {3, 4};

    @Override
    protected int[] getInputSlots() {
        return INPUT;
    }

    @Override
    protected int[] getOutputSlots() {
        return OUTPUT;
    }

    @Override
    protected @NotNull ItemStack getStack(int slot) {
        return new ItemStack(Registries.ITEM.getRandom(getWorld().random).map(RegistryEntry.Reference::value).orElse(Items.ACACIA_BOAT));
    }

    @Override
    public int getPercentage() {
        return 75;
    }

}
