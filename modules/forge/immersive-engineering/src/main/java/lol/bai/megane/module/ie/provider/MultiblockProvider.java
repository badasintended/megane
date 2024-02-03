package lol.bai.megane.module.ie.provider;

import blusunrize.immersiveengineering.api.multiblocks.blocks.logic.IMultiblockBE;
import blusunrize.immersiveengineering.api.multiblocks.blocks.logic.IMultiblockState;
import mcp.mobius.waila.api.IBlockAccessor;
import mcp.mobius.waila.api.IBlockComponentProvider;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IInstanceRegistry;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.ITooltip;
import net.minecraft.world.level.block.entity.BlockEntity;

@SuppressWarnings({"unchecked", "rawtypes", "OverrideOnly"})
public class MultiblockProvider implements IBlockComponentProvider, IDataProvider<BlockEntity> {

    private static final IInstanceRegistry<IBlockComponentProvider> BODY = IInstanceRegistry.create();
    private static final IInstanceRegistry<IDataProvider<IMultiblockBE<IMultiblockState>>> DATA = IInstanceRegistry.create();

    public static <T, S extends IMultiblockState> void addData(IDataProvider<IMultiblockBE<S>> provider, Class<T> clazz) {
        DATA.add(clazz, ((IDataProvider) provider), IRegistrar.DEFAULT_PRIORITY);
    }

    public static <T> void addBody(IBlockComponentProvider provider, Class<T> clazz) {
        BODY.add(clazz, provider, IRegistrar.DEFAULT_PRIORITY);
    }

    @Override
    public void appendBody(ITooltip tooltip, IBlockAccessor accessor, IPluginConfig config) {
        IMultiblockBE<IMultiblockState> be = accessor.getBlockEntity();
        if (be == null) return;

        for (var entry : BODY.get(be.getHelper().getState())) {
            entry.instance().appendBody(tooltip, accessor, config);
        }
    }

    @Override
    public void appendData(IDataWriter data, IServerAccessor<BlockEntity> accessor, IPluginConfig config) {
        var target = ((IMultiblockBE<IMultiblockState>) accessor.getTarget());

        for (var entry : DATA.get(target.getHelper().getState())) {
            entry.instance().appendData(data, (IServerAccessor) accessor, config);
        }
    }

}
