package badasintended.megane.module;

import badasintended.megane.MeganeUtils;
import badasintended.megane.api.MeganeApi;
import badasintended.megane.api.registry.FluidTooltipRegistry;
import badasintended.megane.api.registry.ProgressTooltipRegistry;
import com.github.chainmailstudios.astromine.common.block.entity.ElectricSmelterBlockEntity;
import com.github.chainmailstudios.astromine.common.block.entity.PresserBlockEntity;
import com.github.chainmailstudios.astromine.common.block.entity.TrituratorBlockEntity;
import com.github.chainmailstudios.astromine.common.block.entity.base.DefaultedBlockEntity;
import com.github.chainmailstudios.astromine.common.block.entity.base.DefaultedEnergyFluidBlockEntity;
import com.github.chainmailstudios.astromine.common.block.entity.base.DefaultedEnergyItemBlockEntity;
import com.github.chainmailstudios.astromine.common.block.entity.base.DefaultedFluidBlockEntity;

import java.util.function.Function;

import static com.github.chainmailstudios.astromine.registry.AstromineComponentTypes.FLUID_INVENTORY_COMPONENT;
import static com.github.chainmailstudios.astromine.registry.AstromineComponentTypes.ITEM_INVENTORY_COMPONENT;

public class MeganeAstromine implements MeganeApi {

    private static void registerFluid(Class<? extends DefaultedBlockEntity> clazz) {
        FluidTooltipRegistry.register(clazz,
            b -> b.getComponent(FLUID_INVENTORY_COMPONENT).getSize(),
            (b, i) -> MeganeUtils.fluidName(b.getComponent(FLUID_INVENTORY_COMPONENT).getVolume(i).getFluid()),
            (b, i) -> b.getComponent(FLUID_INVENTORY_COMPONENT).getVolume(i).getFraction().doubleValue(),
            (b, i) -> b.getComponent(FLUID_INVENTORY_COMPONENT).getVolume(i).getSize().doubleValue()
        );
    }

    private static <T extends DefaultedEnergyItemBlockEntity> void registerProgress(Class<T> clazz, Function<T, Integer> progress) {
        ProgressTooltipRegistry.register(clazz,
            b -> 1, b -> 1,
            (b, i) -> b.getComponent(ITEM_INVENTORY_COMPONENT).getStack(0),
            (b, i) -> b.getComponent(ITEM_INVENTORY_COMPONENT).getStack(1),
            progress
        );
    }

    @Override
    public void initialize() {
        registerFluid(DefaultedFluidBlockEntity.class);
        registerFluid(DefaultedEnergyFluidBlockEntity.class);

        registerProgress(ElectricSmelterBlockEntity.class, b -> b.progress);
        registerProgress(PresserBlockEntity.class, b -> b.progress);
        registerProgress(TrituratorBlockEntity.class, b -> b.progress);
    }

}
