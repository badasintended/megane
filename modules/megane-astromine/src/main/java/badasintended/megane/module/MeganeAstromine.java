package badasintended.megane.module;

import badasintended.megane.api.MeganeEntrypoint;
import badasintended.megane.api.registry.ProgressTooltipRegistry;
import com.github.chainmailstudios.astromine.common.block.entity.base.ComponentEnergyInventoryBlockEntity;
import com.github.chainmailstudios.astromine.technologies.common.block.entity.AlloySmelterBlockEntity;
import com.github.chainmailstudios.astromine.technologies.common.block.entity.ElectricSmelterBlockEntity;
import com.github.chainmailstudios.astromine.technologies.common.block.entity.PresserBlockEntity;
import com.github.chainmailstudios.astromine.technologies.common.block.entity.TrituratorBlockEntity;

import java.util.function.Function;

public class MeganeAstromine implements MeganeEntrypoint {

    private static <T extends ComponentEnergyInventoryBlockEntity> void registerProgress(Class<T> clazz, Function<T, Double> progress) {
        ProgressTooltipRegistry.register(clazz, ProgressTooltipRegistry.Provider.of(
            b -> b.getItemInputSlots().toIntArray(),
            b -> b.getItemOutputSlots().toIntArray(),
            (b, i) -> b.getItemComponent().getStack(i),
            b -> (int) (double) progress.apply(b)
        ));
    }

    @Override
    public void initialize() {
        registerProgress(AlloySmelterBlockEntity.class, b -> b.progress);
        registerProgress(ElectricSmelterBlockEntity.class, b -> b.progress);
        registerProgress(PresserBlockEntity.class, b -> b.progress);
        registerProgress(TrituratorBlockEntity.class, b -> b.progress);
    }

}
