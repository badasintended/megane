package badasintended.megane.runtime.mixin;

import mcp.mobius.waila.hud.TooltipRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TooltipRenderer.class)
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
