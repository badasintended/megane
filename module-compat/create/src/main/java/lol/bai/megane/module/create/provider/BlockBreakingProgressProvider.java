package lol.bai.megane.module.create.provider;

import com.simibubi.create.content.contraptions.components.actors.BlockBreakingKineticTileEntity;
import lol.bai.megane.api.provider.base.IoLessProgressProvider;
import lol.bai.megane.module.create.mixin.AccessBlockBreakingKineticTileEntity;

public class BlockBreakingProgressProvider extends IoLessProgressProvider<BlockBreakingKineticTileEntity> {

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
