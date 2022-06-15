package lol.bai.megane.module.lba.provider;

import alexiil.mc.lib.attributes.fluid.FixedFluidInvView;
import alexiil.mc.lib.attributes.fluid.FluidAttributes;
import alexiil.mc.lib.attributes.misc.NullVariant;
import lol.bai.megane.api.provider.FluidProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.Fluid;
import org.jetbrains.annotations.Nullable;

public class LibBlockAttributesFluidProvider extends FluidProvider<BlockEntity> {

    private FixedFluidInvView view;

    @Override
    protected void init() {
        this.view = FluidAttributes.FIXED_INV_VIEW.getFirst(getWorld(), getPos());
    }

    @Override
    public boolean hasFluids() {
        return !(view instanceof NullVariant);
    }

    @Override
    public int getSlotCount() {
        return view.getTankCount();
    }

    @Override
    public @Nullable Fluid getFluid(int slot) {
        return view.getInvFluid(slot).getRawFluid();
    }

    @Override
    public double getStored(int slot) {
        return view.getInvFluid(slot).amount().asLong(1000);
    }

    @Override
    public double getMax(int slot) {
        return view.getMaxAmount_F(slot).asLong(1000);
    }

}
