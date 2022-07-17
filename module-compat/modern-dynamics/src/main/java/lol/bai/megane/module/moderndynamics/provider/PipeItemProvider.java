package lol.bai.megane.module.moderndynamics.provider;

import java.util.List;

import com.google.common.primitives.Ints;
import dev.technici4n.moderndynamics.network.item.TravelingItem;
import dev.technici4n.moderndynamics.pipe.ItemPipeBlockEntity;
import lol.bai.megane.api.provider.ItemProvider;
import lol.bai.megane.module.moderndynamics.mixin.AccessorItemHost;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("UnstableApiUsage")
public class PipeItemProvider extends ItemProvider<ItemPipeBlockEntity> {

    List<TravelingItem> travelingItems;

    @Override
    protected void init() {
        travelingItems = ((AccessorItemHost) getObject().getHosts()[0]).getTravelingItems();
    }

    @Override
    public int getSlotCount() {
        return travelingItems.size();
    }

    @Override
    public @NotNull ItemStack getStack(int slot) {
        TravelingItem item = travelingItems.get(slot);
        return item.variant.toStack(Ints.saturatedCast(item.amount));
    }

}
