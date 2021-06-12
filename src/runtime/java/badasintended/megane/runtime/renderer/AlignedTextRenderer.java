package badasintended.megane.runtime.renderer;

import java.awt.Dimension;

import mcp.mobius.waila.api.ICommonAccessor;
import mcp.mobius.waila.api.ITooltipRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.nbt.NbtCompound;

import static badasintended.megane.runtime.util.Keys.T_KEY;
import static badasintended.megane.runtime.util.Keys.T_VAL;
import static badasintended.megane.runtime.util.Keys.T_VAL_COL;
import static badasintended.megane.runtime.util.RuntimeUtils.align;
import static badasintended.megane.runtime.util.RuntimeUtils.textRenderer;

public class AlignedTextRenderer implements ITooltipRenderer {

    @Override
    public Dimension getSize(NbtCompound data, ICommonAccessor accessor) {
        align = Math.max(align, textRenderer().getWidth(data.getString(T_KEY)));
        return new Dimension(align + textRenderer().getWidth(": " + data.getString(T_VAL)), 10);
    }

    @Override
    public void draw(MatrixStack matrices, NbtCompound data, ICommonAccessor accessor, int x, int y) {
        textRenderer().drawWithShadow(matrices, data.getString(T_KEY), x, y, 0xFFAAAAAA);
        textRenderer().drawWithShadow(matrices, ": ", x + align, y, 0xFFAAAAAA);
        textRenderer().drawWithShadow(matrices, data.getString(T_VAL), x + align + textRenderer().getWidth(": "), y, data.getInt(T_VAL_COL));
    }

}
