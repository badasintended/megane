package lol.bai.megane.module.extragenerators.provider;

import java.util.function.Function;

import io.github.lucaargolo.extragenerators.common.blockentity.AbstractGeneratorBlockEntity;
import io.github.lucaargolo.extragenerators.utils.SimpleSidedInventory;
import lol.bai.megane.api.provider.ItemProvider;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("rawtypes")
public class GeneratorItemProvider<T extends AbstractGeneratorBlockEntity> extends ItemProvider<T> {

    private final Function<T, SimpleSidedInventory> inventoryGetter;
    private SimpleSidedInventory inventory;

    public GeneratorItemProvider(Function<T, SimpleSidedInventory> inventoryGetter) {
        this.inventoryGetter = inventoryGetter;
    }

    @Override
    protected void init() {
        this.inventory = inventoryGetter.apply(getObject());
    }

    @Override
    public int getSlotCount() {
        return inventory.size();
    }

    @Override
    public @NotNull ItemStack getStack(int slot) {
        return inventory.getStack(slot);
    }

}
