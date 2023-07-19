package lol.bai.megane.module.create.provider;

import com.simibubi.create.content.kinetics.base.BlockBreakingKineticBlockEntity;
import lol.bai.megane.api.provider.base.IoLessProgressProvider;
import lol.bai.megane.module.create.mixin.AccessBlockBreakingKineticTileEntity;

public class BlockBreakingProgressProvider extends IoLessProgressProvider<BlockBreakingKineticBlockEntity> {

    AccessBlockBreakingKineticTileEntity access;

    @Override
    protected void init() {
        access = getObjectCasted();
    }

    @Override
    public int getPercentage() {
        return access.megane_destroyProgress() * 10;
    }

}
