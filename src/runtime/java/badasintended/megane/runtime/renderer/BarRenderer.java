package badasintended.megane.runtime.renderer;

import java.awt.Dimension;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import mcp.mobius.waila.api.ICommonAccessor;
import mcp.mobius.waila.api.ITooltipRenderer;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

import static badasintended.megane.runtime.mixin.AccessorTooltipRenderer.getColonOffset;
import static badasintended.megane.runtime.mixin.AccessorTooltipRenderer.setColonOffset;
import static badasintended.megane.runtime.util.Keys.B_COLOR;
import static badasintended.megane.runtime.util.Keys.B_LONG;
import static badasintended.megane.runtime.util.Keys.B_MAX;
import static badasintended.megane.runtime.util.Keys.B_PREFIX;
import static badasintended.megane.runtime.util.Keys.B_STORED;
import static badasintended.megane.runtime.util.Keys.B_TL;
import static badasintended.megane.runtime.util.Keys.B_UNIT;
import static badasintended.megane.runtime.util.RuntimeUtils.drawTexture;
import static badasintended.megane.runtime.util.RuntimeUtils.getBrightness;
import static badasintended.megane.runtime.util.RuntimeUtils.suffix;
import static badasintended.megane.runtime.util.RuntimeUtils.textRenderer;
import static badasintended.megane.util.MeganeUtils.id;
import static net.minecraft.client.gui.DrawableHelper.fill;

public class BarRenderer implements ITooltipRenderer {

    private static final DecimalFormat FORMAT = new DecimalFormat("#.##");
    private static final Identifier TEXTURE = id("textures/bar.png");

    static {
        FORMAT.setRoundingMode(RoundingMode.DOWN);
    }

    private String getValString(NbtCompound data) {
        double stored = data.getDouble(B_STORED);
        double max = data.getDouble(B_MAX);
        String unit = data.getString(B_UNIT);
        boolean verbose = data.getBoolean(B_LONG);

        String storedString;
        if (stored < 0 || stored == Double.MAX_VALUE) {
            storedString = "∞";
        } else {
            storedString = verbose ? String.valueOf(stored) : suffix((long) stored);
        }

        String maxString;
        if (max <= 0 || max == Double.MAX_VALUE) {
            maxString = "∞";
        } else {
            maxString = verbose ? String.valueOf(max) : suffix((long) max);
        }

        return storedString + "/" + maxString + " " + unit;
    }

    @Override
    public Dimension getSize(NbtCompound data, ICommonAccessor accessor) {
        String prefix = data.getString(B_PREFIX);
        if (data.getBoolean(B_TL))
            prefix = I18n.translate(prefix);
        int prefixWidth = textRenderer().getWidth(prefix);
        int textWidth = textRenderer().getWidth(getValString(data));
        setColonOffset(Math.max(prefixWidth, getColonOffset()));
        return new Dimension(getColonOffset() + textRenderer().getWidth(": ") + Math.max(textWidth, 100), 13);
    }

    @Override
    public void draw(MatrixStack matrices, NbtCompound data, ICommonAccessor accessor, int x, int y) {
        double stored = Math.max(data.getDouble(B_STORED), 0);
        double max = Math.max(data.getDouble(B_MAX), 0);

        float ratio = max == 0 ? 1F : ((float) Math.floor((Math.min((float) (stored / max), 1F)) * 100)) / 100F;

        String prefix = data.getString(B_PREFIX);
        textRenderer().drawWithShadow(matrices, prefix, x, y + 2, 0xFFAAAAAA);

        int colon = textRenderer().getWidth(": ");
        textRenderer().drawWithShadow(matrices, ": ", x + getColonOffset(), y + 2, 0xFFAAAAAA);

        int color = data.getInt(B_COLOR);

        int barX = x + getColonOffset() + colon;
        drawTexture(matrices, TEXTURE, barX, y, 100, 11, 0, 0, 1F, 0.5F, color);
        drawTexture(matrices, TEXTURE, barX, y, (int) (ratio * 100), 11, 0, 0.5F, ratio, 1F, color);

        double brightness = getBrightness(color);
        int overlay = 0;

        if (brightness < 0.25)
            overlay = 0x08FFFFFF;
        else if (brightness > 0.90)
            overlay = 0x80000000;
        else if (brightness > 0.80)
            overlay = 0x70000000;
        else if (brightness > 0.70)
            overlay = 0x60000000;
        else if (brightness > 0.60)
            overlay = 0x50000000;
        else if (brightness > 0.50)
            overlay = 0x40000000;

        fill(matrices, barX, y, barX + 100, y + 11, overlay);

        String text = getValString(data);
        int textWidth = textRenderer().getWidth(text);
        float textX = x + getColonOffset() + colon + Math.max((100 - textWidth) / 2F, 0F);
        float textY = y + 2;
        textRenderer().draw(matrices, text, textX, textY, 0xFFAAAAAA);
    }

}
