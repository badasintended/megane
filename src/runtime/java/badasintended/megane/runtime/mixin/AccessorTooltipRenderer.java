package badasintended.megane.runtime.mixin;

import mcp.mobius.waila.hud.TooltipHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TooltipHandler.class)
public interface AccessorTooltipRenderer {

    @Accessor
    static int getColonOffset() {
        throw new AssertionError();
    }

    @Accessor
    static void setColonOffset(int colonOffset) {
        throw new AssertionError();
    }

}
