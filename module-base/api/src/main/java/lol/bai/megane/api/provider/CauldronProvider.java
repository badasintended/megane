package lol.bai.megane.api.provider;

import lol.bai.megane.api.registry.CommonRegistrar;
import net.minecraft.block.BlockState;

/**
 * Base class to cauldron-base fluid provider.
 * <p>
 * Register implementations with {@link CommonRegistrar#addCauldron}.
 *
 * @deprecated use WTHIT API
 */
@Deprecated
public abstract class CauldronProvider extends FluidProvider<Void> {

    protected final BlockState getState() {
        return getWorld().getBlockState(getPos());
    }

    @Override
    public int getSlotCount() {
        return 1;
    }

    @Override
    public double getMax(int slot) {
        return 1000;
    }

}
