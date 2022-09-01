package lol.bai.megane.module.create.provider;

import com.simibubi.create.content.contraptions.processing.BasinOperatingTileEntity;
import com.simibubi.create.content.contraptions.processing.BasinTileEntity;
import lol.bai.megane.module.create.mixin.AccessBasinOperatingTileEntity;
import org.jetbrains.annotations.Nullable;

public abstract class BasinOperatingProgressProvider<T extends BasinOperatingTileEntity> extends BaseBasinProgressProvider<T> {

    private AccessBasinOperatingTileEntity access;
    private BasinTileEntity basin;

    @Override
    protected void init() {
        access = getObjectCasted();
        basin = access.megane_getBasin().orElse(null);

        super.init();
    }

    @Override
    @Nullable BasinTileEntity getBasin() {
        return basin;
    }

}
