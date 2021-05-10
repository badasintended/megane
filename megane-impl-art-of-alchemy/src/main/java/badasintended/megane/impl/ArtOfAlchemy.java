package badasintended.megane.impl;

import badasintended.megane.api.MeganeModule;
import badasintended.megane.api.provider.FluidProvider;
import badasintended.megane.api.provider.ProgressProvider;
import badasintended.megane.impl.mixin.artofalchemy.AProgress;
import badasintended.megane.impl.util.A;
import com.cumulusmc.artofalchemy.blockentity.BlockEntityCalcinator;
import com.cumulusmc.artofalchemy.blockentity.BlockEntityDissolver;
import com.cumulusmc.artofalchemy.blockentity.BlockEntityProjector;
import com.cumulusmc.artofalchemy.blockentity.BlockEntitySynthesizer;
import com.cumulusmc.artofalchemy.fluid.AoAFluids;
import com.cumulusmc.artofalchemy.fluid.MeganeAoAEssentiaFluidInfo;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.inventory.Inventory;

import static badasintended.megane.api.registry.TooltipRegistry.FLUID;
import static badasintended.megane.api.registry.TooltipRegistry.PROGRESS;

public class ArtOfAlchemy implements MeganeModule {

    @Override
    public void initialize() {
        FLUID.register(BlockEntityDissolver.class, FluidProvider.of(
            t -> 1, (t, i) -> AoAFluids.ALKAHEST,
            (t, i) -> (double) t.getAlkahest(),
            (t, i) -> (double) t.getTankSize()
        ));

        FLUID.register(BlockEntityProjector.class, FluidProvider.of(
            t -> 1, (t, i) -> AoAFluids.ALKAHEST,
            (t, i) -> (double) t.getAlkahest(),
            (t, i) -> (double) t.getTankSize()
        ));

        PROGRESS.register(BlockEntityCalcinator.class, ProgressProvider.of(
            t -> A.A_01, t -> A.A_2, Inventory::getStack,
            t -> {
                AProgress a = (AProgress) t;
                return (int) ((double) a.getProgress() / (double) a.getMaxProgress() * 100);
            }
        ));

        PROGRESS.register(BlockEntityDissolver.class, ProgressProvider.of(
            t -> A.A_0, t -> A.A, Inventory::getStack,
            t -> {
                AProgress a = (AProgress) t;
                return (int) ((double) a.getProgress() / (double) a.getMaxProgress() * 100);
            }
        ));

        PROGRESS.register(BlockEntitySynthesizer.class, ProgressProvider.of(
            t -> A.A_01, t -> A.A_2, Inventory::getStack,
            t -> {
                AProgress a = (AProgress) t;
                return (int) ((double) a.getProgress() / (double) a.getMaxProgress() * 100);
            }
        ));

        PROGRESS.register(BlockEntityProjector.class, ProgressProvider.of(
            t -> A.A_0, t -> A.A_1, Inventory::getStack,
            t -> {
                AProgress a = (AProgress) t;
                return (int) ((double) a.getProgress() / (double) a.getMaxProgress() * 100);
            }
        ));

    }

    @Override
    @Environment(EnvType.CLIENT)
    public void initializeClient() {
        MeganeAoAEssentiaFluidInfo.register();
    }

}
