package lol.bai.megane.module.create.provider;

import java.util.List;

import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.foundation.item.SmartInventory;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler;

public class BasinItemProvider extends ItemStackHandlerItemProvider<BasinBlockEntity> {

    @Override
    void appendHandlers(List<ItemStackHandler> handlers) {
        for (SmartInventory inv : getObject().getInvs()) {
            handlers.add(inv);
        }
    }

}
