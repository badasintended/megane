package badasintended.megane.impl;

import badasintended.megane.api.MeganeModule;
import badasintended.megane.api.provider.FluidProvider;
import badasintended.megane.api.provider.ProgressProvider;
import badasintended.megane.api.registry.MeganeClientRegistrar;
import badasintended.megane.api.registry.MeganeRegistrar;
import badasintended.megane.impl.mixin.artofalchemy.AProgress;
import badasintended.megane.impl.util.A;
import com.cumulusmc.artofalchemy.blockentity.BlockEntityCalcinator;
import com.cumulusmc.artofalchemy.blockentity.BlockEntityDissolver;
import com.cumulusmc.artofalchemy.blockentity.BlockEntityProjector;
import com.cumulusmc.artofalchemy.blockentity.BlockEntitySynthesizer;
import com.cumulusmc.artofalchemy.fluid.AoAFluids;
import com.cumulusmc.artofalchemy.fluid.MeganeAoAEssentiaFluidInfo;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventory;

public class ArtOfAlchemy implements MeganeModule {

    @Override
    public void register(MeganeRegistrar registrar) {
        registrar
            .fluid(BlockEntityDissolver.class, FluidProvider.of(
                t -> 1, (t, i) -> AoAFluids.ALKAHEST,
                (t, i) -> (double) t.getAlkahest(),
                (t, i) -> (double) t.getTankSize())
            )
            .fluid(BlockEntityProjector.class, FluidProvider.of(
                t -> 1, (t, i) -> AoAFluids.ALKAHEST,
                (t, i) -> (double) t.getAlkahest(),
                (t, i) -> (double) t.getTankSize())
            )
            .progress(BlockEntityCalcinator.class, ProgressProvider.of(
                t -> A.A_01,
                t -> A.A_2,
                Inventory::getStack,
                this::progress)
            )
            .progress(BlockEntityDissolver.class, ProgressProvider.of(
                t -> A.A_0,
                t -> A.A,
                Inventory::getStack,
                this::progress)
            )
            .progress(BlockEntitySynthesizer.class, ProgressProvider.of(
                t -> A.A_01,
                t -> A.A_2,
                Inventory::getStack,
                this::progress)
            )
            .progress(BlockEntityProjector.class, ProgressProvider.of(
                t -> A.A_0,
                t -> A.A_1,
                Inventory::getStack,
                this::progress)
            );
    }

    @Override
    public void registerClient(MeganeClientRegistrar registrar) {
        MeganeAoAEssentiaFluidInfo.register(registrar);
    }

    private int progress(BlockEntity blockEntity) {
        AProgress a = (AProgress) blockEntity;
        return (int) ((double) a.getProgress() / (double) a.getMaxProgress() * 100);
    }

}
