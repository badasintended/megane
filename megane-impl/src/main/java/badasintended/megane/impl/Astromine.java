package badasintended.megane.impl;

import badasintended.megane.api.MeganeEntrypoint;
import badasintended.megane.api.registry.ProgressTooltipRegistry;
import badasintended.megane.impl.util.Arrays;
import com.github.chainmailstudios.astromine.common.block.entity.base.ComponentEnergyFluidBlockEntity;
import com.github.chainmailstudios.astromine.common.block.entity.base.ComponentEnergyInventoryBlockEntity;
import com.github.chainmailstudios.astromine.technologies.common.block.entity.*;
import net.minecraft.item.ItemStack;

import java.util.function.Function;

public class Astromine implements MeganeEntrypoint {

    private static final String[] DEP = new String[]{"astromine-technologies"};

    @Override
    public String[] dependencies() {
        return DEP;
    }

    private <T extends ComponentEnergyInventoryBlockEntity> void progressItem(Class<T> clazz, int[] input, int[] output, Function<T, Double> progress) {
        ProgressTooltipRegistry.register(clazz, ProgressTooltipRegistry.Provider.of(
            b -> input,
            b -> output,
            (b, i) -> b.getItemComponent().getStack(i),
            b -> (int) (progress.apply(b) * 100)
        ));
    }

    private <T extends ComponentEnergyFluidBlockEntity> void progressFluid(Class<T> clazz, Function<T, Double> progress) {
        ProgressTooltipRegistry.register(clazz, ProgressTooltipRegistry.Provider.of(
            b -> Arrays.A,
            b -> Arrays.A,
            (b, i) -> ItemStack.EMPTY,
            b -> (int) (progress.apply(b) * 100)
        ));
    }

    @Override
    public void initialize() {
        progressItem(AlloySmelterBlockEntity.class, Arrays.A_01, Arrays.A_2, b -> b.progress / b.limit);
        progressItem(ElectricSmelterBlockEntity.class, Arrays.A_1, Arrays.A_0, b -> b.progress / b.limit);
        progressItem(PresserBlockEntity.class, Arrays.A_1, Arrays.A_0, b -> b.progress / b.limit);
        progressItem(TrituratorBlockEntity.class, Arrays.A_1, Arrays.A_0, b -> b.progress / b.limit);
        progressItem(SolidGeneratorBlockEntity.class, Arrays.A_0, Arrays.A, b -> b.progress / b.limit);
        progressFluid(FluidMixerBlockEntity.class, b -> b.progress / b.limit);
        progressFluid(LiquidGeneratorBlockEntity.class, b -> b.progress / b.limit);
        progressFluid(ElectrolyzerBlockEntity.class, b -> b.progress / b.limit);
    }

}
