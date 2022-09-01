package lol.bai.megane.module.create.provider;

import java.util.List;

import com.simibubi.create.content.contraptions.processing.BasinTileEntity;
import com.simibubi.create.foundation.item.SmartInventory;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler;

public class BasinItemProvider extends ItemStackHandlerItemProvider<BasinTileEntity> {

    @Override
    void appendHandlers(List<ItemStackHandler> handlers) {
        for (SmartInventory inv : getObject().getInvs()) {
            handlers.add(inv);
        }
    }

}
