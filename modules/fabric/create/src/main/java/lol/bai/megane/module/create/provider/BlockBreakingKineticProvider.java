package lol.bai.megane.module.create.provider;

import com.simibubi.create.content.kinetics.base.BlockBreakingKineticBlockEntity;
import lol.bai.megane.module.create.mixin.AccessBlockBreakingKineticTileEntity;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.ProgressData;

public class BlockBreakingKineticProvider implements IDataProvider<BlockBreakingKineticBlockEntity> {

    @Override
    public void appendData(IDataWriter data, IServerAccessor<BlockBreakingKineticBlockEntity> accessor, IPluginConfig config) {
        data.add(ProgressData.class, res -> {
            var target = (BlockBreakingKineticBlockEntity & AccessBlockBreakingKineticTileEntity) accessor.getTarget();

            res.add(ProgressData.ratio(target.megane_destroyProgress() / 10.0f));
        });
    }

}
