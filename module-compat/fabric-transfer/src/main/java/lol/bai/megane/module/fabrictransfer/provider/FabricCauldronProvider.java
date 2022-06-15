package lol.bai.megane.module.fabrictransfer.provider;

import lol.bai.megane.api.provider.CauldronProvider;
import net.fabricmc.fabric.api.transfer.v1.fluid.CauldronFluidContent;
import net.minecraft.fluid.Fluid;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("UnstableApiUsage")
public class FabricCauldronProvider extends CauldronProvider {

    private CauldronFluidContent content;

    @Override
    protected void init() {
        this.content = CauldronFluidContent.getForBlock(getState().getBlock());
    }

    @Override
    public boolean hasFluids() {
        return content != null;
    }

    @Override
    public @Nullable Fluid getFluid(int slot) {
        return content.fluid;
    }

    @Override
    public double getStored(int slot) {
        return droplets(content.currentLevel(getState()) * content.amountPerLevel);
    }

    @Override
    public double getMax(int slot) {
        return droplets(content.maxLevel * content.amountPerLevel);
    }

}
