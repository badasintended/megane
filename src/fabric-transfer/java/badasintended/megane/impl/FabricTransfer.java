package badasintended.megane.impl;

import badasintended.megane.api.MeganeModule;
import badasintended.megane.api.provider.CauldronFluidProvider;
import badasintended.megane.api.registry.MeganeRegistrar;
import net.fabricmc.fabric.api.transfer.v1.fluid.CauldronFluidContent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;

@SuppressWarnings({"deprecation", "UnstableApiUsage"})
public class FabricTransfer implements MeganeModule {

    @Override
    public void register(MeganeRegistrar registrar) {
        registrar
            .cauldron(Block.class, new CauldronFluidProvider() {
                CauldronFluidContent content;

                @Override
                public boolean hasFluid(BlockState state) {
                    content = CauldronFluidContent.getForBlock(state.getBlock());
                    return content != null;
                }

                @Override
                public Fluid getFluid(BlockState state) {
                    return content.fluid;
                }

                @Override
                public double getStored(BlockState state) {
                    return content.maxLevel * content.amountPerLevel / 81.0;
                }

                @Override
                public double getMax(BlockState state) {
                    return content.currentLevel(state) * content.amountPerLevel / 81.0;
                }
            });
    }

}
