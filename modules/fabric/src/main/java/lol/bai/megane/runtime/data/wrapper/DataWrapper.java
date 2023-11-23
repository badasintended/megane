package lol.bai.megane.runtime.data.wrapper;

import lol.bai.megane.api.provider.AbstractProvider;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IServerAccessor;
import net.minecraft.core.BlockPos;

@SuppressWarnings({"deprecation", "rawtypes", "unchecked"})
public abstract class DataWrapper<P extends AbstractProvider, T> implements IDataProvider<T> {

    protected final P megane;

    public DataWrapper(P megane) {
        this.megane = megane;
    }

    protected void setContext(IServerAccessor<T> accessor, BlockPos pos) {
        megane.setContext(accessor.getWorld(), pos, accessor.getHitResult(), accessor.getPlayer(), accessor.getTarget());
    }

}
