package lol.bai.megane.api.provider;

import net.minecraft.block.BlockState;

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
