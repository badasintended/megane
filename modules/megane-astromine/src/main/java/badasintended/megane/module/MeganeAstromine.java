package badasintended.megane.module;

import badasintended.megane.api.MeganeApi;
import badasintended.megane.api.registry.FluidTooltipRegistry;
import com.github.chainmailstudios.astromine.common.block.entity.base.DefaultedBlockEntity;
import com.github.chainmailstudios.astromine.common.block.entity.base.DefaultedEnergyFluidBlockEntity;
import com.github.chainmailstudios.astromine.common.block.entity.base.DefaultedFluidBlockEntity;
import com.github.chainmailstudios.astromine.registry.AstromineComponentTypes;
import org.apache.commons.lang3.StringUtils;

public class MeganeAstromine implements MeganeApi {

    private static void registerFluid(Class<? extends DefaultedBlockEntity> clazz) {
        FluidTooltipRegistry.register(clazz,
            b -> b.getSidedComponent(null, AstromineComponentTypes.FLUID_INVENTORY_COMPONENT).getSize(),
            (b, i) -> StringUtils.capitalize(b.getSidedComponent(null, AstromineComponentTypes.FLUID_INVENTORY_COMPONENT).getVolume(i).getFluidIdentifier().getPath()),
            (b, i) -> b.getSidedComponent(null, AstromineComponentTypes.FLUID_INVENTORY_COMPONENT).getVolume(i).getFraction().doubleValue(),
            (b, i) -> b.getSidedComponent(null, AstromineComponentTypes.FLUID_INVENTORY_COMPONENT).getVolume(i).getSize().doubleValue()
        );
    }

    @Override
    public void onMeganeInitialize() {
        registerFluid(DefaultedFluidBlockEntity.class);
        registerFluid(DefaultedEnergyFluidBlockEntity.class);
    }

}
