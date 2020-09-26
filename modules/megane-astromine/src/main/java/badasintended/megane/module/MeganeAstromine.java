package badasintended.megane.module;

import badasintended.megane.api.MeganeEntrypoint;
import badasintended.megane.api.registry.ProgressTooltipRegistry;
import com.github.chainmailstudios.astromine.common.block.entity.base.ComponentEnergyFluidBlockEntity;
import com.github.chainmailstudios.astromine.common.block.entity.base.ComponentEnergyInventoryBlockEntity;
import com.github.chainmailstudios.astromine.technologies.common.block.entity.*;
import net.minecraft.item.ItemStack;

import java.util.function.Function;

public class MeganeAstromine implements MeganeEntrypoint {

    private static final int[] ARRAY_01 = new int[]{0, 1};
    private static final int[] ARRAY_2 = new int[]{2};
    private static final int[] ARRAY_0 = new int[]{0};
    private static final int[] ARRAY_1 = new int[]{1};
    private static final int[] ARRAY_EMPTY = new int[0];

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
            b -> ARRAY_EMPTY,
            b -> ARRAY_EMPTY,
            (b, i) -> ItemStack.EMPTY,
            b -> (int) (progress.apply(b) * 100)
        ));
    }

    @Override
    public void initialize() {
        progressItem(AlloySmelterBlockEntity.class, ARRAY_01, ARRAY_2, b -> b.progress / b.limit);
        progressItem(ElectricSmelterBlockEntity.class, ARRAY_1, ARRAY_0, b -> b.progress / b.limit);
        progressItem(PresserBlockEntity.class, ARRAY_1, ARRAY_0, b -> b.progress / b.limit);
        progressItem(TrituratorBlockEntity.class, ARRAY_1, ARRAY_0, b -> b.progress / b.limit);
        progressItem(SolidGeneratorBlockEntity.class, ARRAY_0, ARRAY_EMPTY, b -> b.progress / b.limit);
        progressFluid(FluidMixerBlockEntity.class, b -> b.progress / b.limit);
        progressFluid(LiquidGeneratorBlockEntity.class, b -> b.progress / b.limit);
        progressFluid(ElectrolyzerBlockEntity.class, b -> b.progress / b.limit);
    }

}
