package badasintended.megane.runtime.renderer;

import java.awt.Dimension;

import mcp.mobius.waila.api.ICommonAccessor;
import mcp.mobius.waila.api.ITooltipRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.nbt.CompoundTag;

import static badasintended.megane.runtime.util.RuntimeUtils.align;
import static badasintended.megane.runtime.util.RuntimeUtils.textRenderer;
import static badasintended.megane.util.MeganeUtils.key;

public class AlignedTextRenderer implements ITooltipRenderer {

    @Override
    public Dimension getSize(CompoundTag data, ICommonAccessor accessor) {
        align = Math.max(align, textRenderer().getWidth(data.getString(key("key"))));
        return new Dimension(align + textRenderer().getWidth(": " + data.getString(key("value"))), 10);
    }

    @Override
    public void draw(MatrixStack matrices, CompoundTag data, ICommonAccessor accessor, int x, int y) {
        textRenderer().drawWithShadow(matrices, data.getString(key("key")), x, y, 0xFFAAAAAA);
        textRenderer().drawWithShadow(matrices, ": ", x + align, y, 0xFFAAAAAA);
        textRenderer().drawWithShadow(matrices, data.getString(key("value")), x + align + textRenderer().getWidth(": "), y, data.getInt(key("valColor")));
    }

}
