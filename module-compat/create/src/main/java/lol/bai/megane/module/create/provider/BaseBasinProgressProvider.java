package lol.bai.megane.module.create.provider;

import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.content.processing.basin.BasinOperatingBlockEntity;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public abstract class BaseBasinProgressProvider<T> extends ItemStackHandlerProgressProvider<T> {

    private BasinBlockEntity basin;
    private BasinOperatingBlockEntity operator;

    @Nullable
    abstract BasinBlockEntity getBasin();

    @Nullable
    abstract BasinOperatingBlockEntity getOperator();

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
