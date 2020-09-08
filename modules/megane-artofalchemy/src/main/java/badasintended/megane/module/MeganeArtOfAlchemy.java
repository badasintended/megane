package badasintended.megane.module;

import badasintended.megane.api.MeganeApi;
import badasintended.megane.api.registry.FluidTooltipRegistry;
import badasintended.megane.api.registry.ProgressTooltipRegistry;
import io.github.synthrose.artofalchemy.blockentity.BlockEntityCalcinator;
import io.github.synthrose.artofalchemy.blockentity.BlockEntityDissolver;
import io.github.synthrose.artofalchemy.util.ImplementedInventory;
import net.minecraft.client.resource.language.I18n;

public class MeganeArtOfAlchemy implements MeganeApi {

    @Override
    public void initialize() {
        FluidTooltipRegistry.register(
            BlockEntityDissolver.class, b -> 1,
            (b, i) -> I18n.translate("fluid.artofalchemy.alkahest"),
            (b, i) -> (double) b.getPropertyDelegate().get(0),
            (b, i) -> (double) b.getTankSize()
        );

        ProgressTooltipRegistry.register(
            BlockEntityCalcinator.class, b -> 2, b -> 1,
            ImplementedInventory::getStack,
            (b, i) -> b.getStack(i + 2),
            b -> (int) (b.getPropertyDelegate().get(2) / (float) b.getOperationTime() * 100F)
        );
    }

}
