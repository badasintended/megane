package lol.bai.megane.module.ae2.mixin;

import appeng.api.integrations.igtooltip.TooltipBuilder;
import appeng.api.integrations.igtooltip.TooltipContext;
import appeng.integration.modules.igtooltip.blocks.PowerStorageDataProvider;
import net.minecraft.block.entity.BlockEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

@SuppressWarnings("UnstableApiUsage")
@Mixin(PowerStorageDataProvider.class)
public class MixinPowerStorageDataProvider {

    @Unique
    private static final Logger LOGGER = LoggerFactory.getLogger("MixinPowerStorageDataProvider");

    @Unique
    private static boolean firstCall = true;

    /**
     * @author deirn
     * @reason Remove AE2's own energy tooltip, since megane provides it
     */
    @Overwrite
    public void buildTooltip(BlockEntity object, TooltipContext context, TooltipBuilder tooltip) {
        if (firstCall) {
            firstCall = false;
            LOGGER.info("[Megane] Replaced AE2's own energy tooltip with Megane's");
        }
    }

}
