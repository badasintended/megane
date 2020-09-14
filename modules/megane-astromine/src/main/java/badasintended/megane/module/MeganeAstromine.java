package badasintended.megane.module;

import badasintended.megane.api.MeganeEntrypoint;
import badasintended.megane.api.registry.ProgressTooltipRegistry;
import com.github.chainmailstudios.astromine.common.block.entity.base.ComponentEnergyFluidBlockEntity;
import com.github.chainmailstudios.astromine.common.block.entity.base.ComponentEnergyInventoryBlockEntity;
import com.github.chainmailstudios.astromine.technologies.common.block.entity.*;
import net.minecraft.item.ItemStack;

import java.util.function.Function;

public class MeganeAstromine implements MeganeEntrypoint {

    private static final int[] EMPTY_INT_ARRAY = new int[0];

    private <T extends ComponentEnergyInventoryBlockEntity> void progressItem(Class<T> clazz, Function<T, Double> progress) {
        ProgressTooltipRegistry.register(clazz, ProgressTooltipRegistry.Provider.of(
            b -> b.getItemInputSlots().toIntArray(),
            b -> b.getItemOutputSlots().toIntArray(),
            (b, i) -> b.getItemComponent().getStack(i),
            b -> (int) (progress.apply(b) / 2)
        ));
    }

    private <T extends ComponentEnergyFluidBlockEntity> void progressFluid(Class<T> clazz, Function<T, Double> progress) {
        ProgressTooltipRegistry.register(clazz, ProgressTooltipRegistry.Provider.of(
            b -> EMPTY_INT_ARRAY,
            b -> EMPTY_INT_ARRAY,
            (b, i) -> ItemStack.EMPTY,
            b -> (int) (progress.apply(b) / 2)
        ));
    }

    @Override
    public void initialize() {
        progressItem(AlloySmelterBlockEntity.class, b -> b.progress);
        progressItem(ElectricSmelterBlockEntity.class, b -> b.progress);
        progressItem(PresserBlockEntity.class, b -> b.progress);
        progressItem(TrituratorBlockEntity.class, b -> b.progress);
        progressItem(SolidGeneratorBlockEntity.class, b -> b.progress);
        progressFluid(LiquidGeneratorBlockEntity.class, b -> b.progress);
        progressFluid(ElectrolyzerBlockEntity.class, b -> b.progress);
    }

}
