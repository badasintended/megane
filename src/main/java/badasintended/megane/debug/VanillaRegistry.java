package badasintended.megane.debug;

import badasintended.megane.api.MeganeApi;
import badasintended.megane.api.registry.EnergyTooltipRegistry;
import badasintended.megane.api.registry.FluidTooltipRegistry;
import net.minecraft.block.entity.LockableContainerBlockEntity;

public class VanillaRegistry implements MeganeApi {

    @Override
    public void onMeganeInitialize() {
        EnergyTooltipRegistry.register(LockableContainerBlockEntity.class, "CHEST", l -> 69d, l -> 420d);
        FluidTooltipRegistry.register(LockableContainerBlockEntity.class, c -> 1, (c, i) -> "bruh", (c, i) -> 25D, (c, i) -> 100D);
    }

}
