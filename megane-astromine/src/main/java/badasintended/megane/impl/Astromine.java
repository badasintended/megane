package badasintended.megane.impl;

import java.util.function.Function;

import badasintended.megane.api.MeganeModule;
import badasintended.megane.api.provider.FluidInfoProvider;
import badasintended.megane.api.provider.ProgressProvider;
import badasintended.megane.api.registry.MeganeClientRegistrar;
import badasintended.megane.api.registry.MeganeRegistrar;
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
import net.minecraft.item.ItemStack;

public class Astromine implements MeganeModule {

    private MeganeRegistrar registrar;

    private <T extends ComponentEnergyInventoryBlockEntity> void progressItem(Class<T> clazz, int[] input, int[] output, Function<T, Double> progress) {
        registrar.progress(clazz, ProgressProvider.of(
            b -> input,
            b -> output,
            (b, i) -> b.getItemComponent().getStack(i),
            b -> (int) (progress.apply(b) * 100)
        ));
    }

    private <T extends ComponentEnergyFluidBlockEntity> void progressFluid(Class<T> clazz, Function<T, Double> progress) {
        registrar.progress(clazz, ProgressProvider.of(
            b -> A.A,
            b -> A.A,
            (b, i) -> ItemStack.EMPTY,
            b -> (int) (progress.apply(b) * 100)
        ));
    }

    @Override
    public void register(MeganeRegistrar registrar) {
        this.registrar = registrar;
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
    public void registerClient(MeganeClientRegistrar registrar) {
        registrar.fluid(ExtendedFluid.class, FluidInfoProvider.of(ExtendedFluid::getTintColor, t -> t.getBlock().getName()))
            .energy("astromine", 0x356D95, "E");
    }

}
