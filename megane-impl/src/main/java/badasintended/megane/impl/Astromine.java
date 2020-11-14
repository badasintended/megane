package badasintended.megane.impl;

import java.util.function.Function;

import badasintended.megane.api.MeganeEntrypoint;
import badasintended.megane.api.provider.FluidInfoProvider;
import badasintended.megane.api.provider.ProgressProvider;
import badasintended.megane.impl.util.A;
import com.github.chainmailstudios.astromine.common.block.entity.base.ComponentEnergyFluidBlockEntity;
import com.github.chainmailstudios.astromine.common.block.entity.base.ComponentEnergyInventoryBlockEntity;
import com.github.chainmailstudios.astromine.common.fluid.ExtendedFluid;
import com.github.chainmailstudios.astromine.technologies.common.block.entity.AlloySmelterBlockEntity;
import com.github.chainmailstudios.astromine.technologies.common.block.entity.ElectricSmelterBlockEntity;
import com.github.chainmailstudios.astromine.technologies.common.block.entity.ElectrolyzerBlockEntity;
import com.github.chainmailstudios.astromine.technologies.common.block.entity.FluidMixerBlockEntity;
import com.github.chainmailstudios.astromine.technologies.common.block.entity.LiquidGeneratorBlockEntity;
import com.github.chainmailstudios.astromine.technologies.common.block.entity.PresserBlockEntity;
import com.github.chainmailstudios.astromine.technologies.common.block.entity.SolidGeneratorBlockEntity;
import com.github.chainmailstudios.astromine.technologies.common.block.entity.TrituratorBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemStack;

import static badasintended.megane.api.registry.TooltipRegistry.FLUID_INFO;
import static badasintended.megane.api.registry.TooltipRegistry.PROGRESS;

public class Astromine implements MeganeEntrypoint {

    @Override
    public String[] dependencies() {
        return new String[]{"astromine-technologies"};
    }

    private <T extends ComponentEnergyInventoryBlockEntity> void progressItem(Class<T> clazz, int[] input, int[] output, Function<T, Double> progress) {
        PROGRESS.register(clazz, ProgressProvider.of(
            b -> input,
            b -> output,
            (b, i) -> b.getItemComponent().getStack(i),
            b -> (int) (progress.apply(b) * 100)
        ));
    }

    private <T extends ComponentEnergyFluidBlockEntity> void progressFluid(Class<T> clazz, Function<T, Double> progress) {
        PROGRESS.register(clazz, ProgressProvider.of(
            b -> A.A,
            b -> A.A,
            (b, i) -> ItemStack.EMPTY,
            b -> (int) (progress.apply(b) * 100)
        ));
    }

    @Override
    public void initialize() {
        progressItem(AlloySmelterBlockEntity.class, A.A_01, A.A_2, b -> b.progress / b.limit);
        progressItem(ElectricSmelterBlockEntity.class, A.A_1, A.A_0, b -> b.progress / b.limit);
        progressItem(PresserBlockEntity.class, A.A_1, A.A_0, b -> b.progress / b.limit);
        progressItem(TrituratorBlockEntity.class, A.A_1, A.A_0, b -> b.progress / b.limit);
        progressItem(SolidGeneratorBlockEntity.class, A.A_0, A.A, b -> b.progress / b.limit);
        progressFluid(FluidMixerBlockEntity.class, b -> b.progress / b.limit);
        progressFluid(LiquidGeneratorBlockEntity.class, b -> b.progress / b.limit);
        progressFluid(ElectrolyzerBlockEntity.class, b -> b.progress / b.limit);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void initializeClient() {
        FLUID_INFO.register(ExtendedFluid.class, FluidInfoProvider.of(ExtendedFluid::getTintColor, t -> t.getBlock().getName()));
    }

}
