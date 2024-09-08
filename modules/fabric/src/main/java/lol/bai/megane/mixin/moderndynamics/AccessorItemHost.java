package lol.bai.megane.mixin.moderndynamics;

import java.util.List;

import dev.technici4n.moderndynamics.network.item.ItemHost;
import dev.technici4n.moderndynamics.network.item.TravelingItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemHost.class)
public interface AccessorItemHost {

    @Accessor
    List<TravelingItem> getTravelingItems();

}
