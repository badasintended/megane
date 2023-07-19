package lol.bai.megane.module.create.provider;

import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.content.processing.basin.BasinOperatingBlockEntity;
import lol.bai.megane.module.create.mixin.AccessBasinOperatingTileEntity;
import org.jetbrains.annotations.Nullable;

public abstract class BasinOperatingProgressProvider<T extends BasinOperatingBlockEntity> extends BaseBasinProgressProvider<T> {

    private AccessBasinOperatingTileEntity access;
    private BasinBlockEntity basin;

    @Override
    protected void init() {
        access = getObjectCasted();
        basin = access.megane_getBasin().orElse(null);

        super.init();
    }

    @Override
    @Nullable BasinBlockEntity getBasin() {
        return basin;
    }

}
