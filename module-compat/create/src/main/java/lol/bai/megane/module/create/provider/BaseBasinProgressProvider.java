package lol.bai.megane.module.create.provider;

import com.simibubi.create.content.contraptions.processing.BasinOperatingTileEntity;
import com.simibubi.create.content.contraptions.processing.BasinTileEntity;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public abstract class BaseBasinProgressProvider<T> extends ItemStackHandlerProgressProvider<T> {

    private BasinTileEntity basin;
    private BasinOperatingTileEntity operator;

    @Nullable
    abstract BasinTileEntity getBasin();

    @Nullable
    abstract BasinOperatingTileEntity getOperator();

    @Override
    protected void init() {
        basin = getBasin();
        operator = getOperator();
        super.init();
    }

    @Override
    public boolean hasProgress() {
        return operator != null;
    }

    @Override
    @Nullable ItemStackHandler getInputStackHandler() {
        return basin.getInputInventory();
    }

    @Override
    @Nullable ItemStackHandler getOutputStackHandler() {
        return basin.getOutputInventory();
    }

}
