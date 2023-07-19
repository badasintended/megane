package lol.bai.megane.module.create.provider;

import com.simibubi.create.content.schematics.cannon.SchematicannonBlockEntity;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class SchematicannonProgressProvider extends ItemStackHandlerProgressProvider<SchematicannonBlockEntity> {

    @Override
    @Nullable ItemStackHandler getInputStackHandler() {
        return getObject().inventory;
    }

    @Override
    @Nullable ItemStackHandler getOutputStackHandler() {
        return null;
    }

    @Override
    public int getPercentage() {
        return currentPercentage(getObject().blocksPlaced, getObject().blocksToPlace);
    }

}
