package lol.bai.megane.module.create.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler;
import lol.bai.megane.api.provider.ItemProvider;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public abstract class ItemStackHandlerItemProvider<T> extends ItemProvider<T> {

    private final List<ItemStackHandler> handlers = new ArrayList<>();
    private int slotCount;

    abstract void appendHandlers(List<ItemStackHandler> handlers);

    @Override
    protected void init() {
        handlers.clear();
        appendHandlers(handlers);
        slotCount = handlers.stream().mapToInt(ItemStackHandler::getSlots).sum();
    }

    @Override
    public int getSlotCount() {
        return slotCount;
    }

    @Override
    public @NotNull ItemStack getStack(int slot) {
        int pad = 0;
        for (ItemStackHandler handler : handlers) {
            if ((slot - pad) < handler.getSlots()) {
                return handler.getStackInSlot(slot - pad);
            }
            pad += handler.getSlots();
        }
        return ItemStack.EMPTY;
    }

    public static class Single<T> extends ItemStackHandlerItemProvider<T> {

        private final Function<T, ItemStackHandler> getter;

        public Single(Function<T, ItemStackHandler> getter) {
            this.getter = getter;
        }

        @Override
        void appendHandlers(List<ItemStackHandler> handlers) {
            handlers.add(getter.apply(getObject()));
        }

    }

}
