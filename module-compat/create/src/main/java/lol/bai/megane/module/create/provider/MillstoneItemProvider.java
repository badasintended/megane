package lol.bai.megane.module.create.provider;

import java.util.List;

import com.simibubi.create.content.kinetics.millstone.MillstoneBlockEntity;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler;

public class MillstoneItemProvider extends ItemStackHandlerItemProvider<MillstoneBlockEntity> {

    @Override
    void appendHandlers(List<ItemStackHandler> handlers) {
        handlers.add(getObject().inputInv);
        handlers.add(getObject().outputInv);
    }

}
