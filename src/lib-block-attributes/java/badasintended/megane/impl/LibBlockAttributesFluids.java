package badasintended.megane.impl;

import alexiil.mc.lib.attributes.fluid.FixedFluidInvView;
import alexiil.mc.lib.attributes.fluid.FluidAttributes;
import alexiil.mc.lib.attributes.misc.NullVariant;
import badasintended.megane.api.MeganeModule;
import badasintended.megane.api.provider.FluidProvider;
import badasintended.megane.api.registry.MeganeRegistrar;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.Fluid;
import org.jetbrains.annotations.Nullable;

public class LibBlockAttributesFluids implements MeganeModule {

    @Override
    public void register(MeganeRegistrar registrar) {
        registrar.fluid(1100, BlockEntity.class, new FluidProvider<>() {
            FixedFluidInvView view;

            @Override
            public boolean hasFluid(BlockEntity blockEntity) {
                view = FluidAttributes.FIXED_INV_VIEW.getFirst(blockEntity.getWorld(), blockEntity.getPos());
                return !(view instanceof NullVariant);
            }

            @Override
            public int getSlotCount(BlockEntity blockEntity) {
                return view.getTankCount();
            }

            @Override
            public @Nullable Fluid getFluid(BlockEntity blockEntity, int slot) {
                return view.getInvFluid(slot).getRawFluid();
            }

            @Override
            public double getStored(BlockEntity blockEntity, int slot) {
                return view.getInvFluid(slot).amount().asInt(1000);
            }

            @Override
            public double getMax(BlockEntity blockEntity, int slot) {
                return view.getMaxAmount_F(slot).asInt(1000);
            }
        });
    }

}
