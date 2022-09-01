package lol.bai.megane.module.create.provider;

import java.util.List;

import com.simibubi.create.content.contraptions.components.millstone.MillstoneTileEntity;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler;

public class MillstoneItemProvider extends ItemStackHandlerItemProvider<MillstoneTileEntity> {

    @Override
    void appendHandlers(List<ItemStackHandler> handlers) {
        handlers.add(getObject().inputInv);
        handlers.add(getObject().outputInv);
    }

}
