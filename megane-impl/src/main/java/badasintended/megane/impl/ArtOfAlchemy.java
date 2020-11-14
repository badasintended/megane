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
import com.cumulusmc.artofalchemy.blockentity.BlockEntityTank;
import com.cumulusmc.artofalchemy.fluid.AoAFluids;
import com.cumulusmc.artofalchemy.fluid.MeganeAoAEssentiaFluidInfo;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.fluid.EmptyFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.Inventory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.registry.Registry;

import static badasintended.megane.api.registry.TooltipRegistry.FLUID;
import static badasintended.megane.api.registry.TooltipRegistry.FLUID_INFO;
import static badasintended.megane.api.registry.TooltipRegistry.PROGRESS;
import static badasintended.megane.util.MeganeUtils.id;

public class ArtOfAlchemy implements MeganeModule {

    private static final Fluid FAKE_ESSENTIA = new EmptyFluid();
    private static final Text ESSENTIA_NAME = new TranslatableText("essentia.artofalchemy");

    @Override
    public void initialize() {
        // fake fluid for showing total essentia count
        // since individual essentia value doesn't actually make sense in bar form
        // this is the most brilliant answer for the problem, i think
        Registry.register(Registry.FLUID, id("aoa_fake_essentia"), FAKE_ESSENTIA);

        FLUID.register(BlockEntityDissolver.class, FluidProvider.of(
            t -> 2, (t, i) -> i == 0 ? AoAFluids.ALKAHEST : FAKE_ESSENTIA,
            (t, i) -> (double) (i == 0 ? t.getAlkahest() : t.getContainer().getCount()),
            (t, i) -> (double) (i == 0 ? t.getTankSize() : t.getContainer().getCapacity())
        ));

        FLUID.register(BlockEntitySynthesizer.class, FluidProvider.of(
            t -> 1, (t, i) -> FAKE_ESSENTIA,
            (t, i) -> (double) t.getContainer().getCount(),
            (t, i) -> (double) t.getContainer().getCapacity()
        ));

        FLUID.register(BlockEntityProjector.class, FluidProvider.of(
            t -> 1, (t, i) -> AoAFluids.ALKAHEST,
            (t, i) -> (double) t.getAlkahest(),
            (t, i) -> (double) t.getTankSize()
        ));

        FLUID.register(BlockEntityTank.class, FluidProvider.of(
            t -> 1, (t, i) -> FAKE_ESSENTIA,
            (t, i) -> (double) t.getContainer().getCount(),
            (t, i) -> (double) t.getContainer().getCapacity()
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
        FLUID_INFO.register(FAKE_ESSENTIA, 0x946645, ESSENTIA_NAME);
        MeganeAoAEssentiaFluidInfo.register();
    }

}
