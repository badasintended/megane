package badasintended.megane.runtime.renderer;

import mcp.mobius.waila.api.ICommonAccessor;
import mcp.mobius.waila.api.ITooltipRenderer;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;

import java.awt.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import static badasintended.megane.runtime.util.RuntimeUtils.*;
import static badasintended.megane.util.MeganeUtils.id;
import static badasintended.megane.util.MeganeUtils.key;

public class BarRenderer implements ITooltipRenderer {

    private static final DecimalFormat FORMAT = new DecimalFormat("#.##");
    private static final Identifier TEXTURE = id("textures/bar.png");

    static {
        FORMAT.setRoundingMode(RoundingMode.DOWN);
    }

    private String getValString(CompoundTag data) {
        double stored = data.getDouble(key("stored"));
        double max = data.getDouble(key("max"));
        String unit = data.getString(key("unit"));
        boolean verbose = data.getBoolean(key("verbose"));

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
    public Dimension getSize(CompoundTag data, ICommonAccessor accessor) {
        String prefix = data.getString(key("prefix"));
        if (data.getBoolean(key("translate"))) prefix = I18n.translate(prefix);
        int prefixWidth = textRenderer().getWidth(prefix);
        int textWidth = textRenderer().getWidth(getValString(data));
        align = Math.max(prefixWidth, align);
        return new Dimension(align + textRenderer().getWidth(": ") + Math.max(textWidth, 100), 13);
    }

    @Override
    public void draw(MatrixStack matrices, CompoundTag data, ICommonAccessor accessor, int x, int y) {
        double stored = Math.max(data.getDouble(key("stored")), 0);
        double max = Math.max(data.getDouble(key("max")), 0);

        float ratio = max == 0 ? 1F : ((float) Math.floor((Math.min((float) (stored / max), 1F)) * 100)) / 100F;

        int color = data.getInt(key("color"));

        String prefix = data.getString(key("prefix"));
        textRenderer().drawWithShadow(matrices, prefix, x, y + 2, 0xFFAAAAAA);

        int colon = textRenderer().getWidth(": ");
        textRenderer().drawWithShadow(matrices, ": ", x + align, y + 2, 0xFFAAAAAA);

        drawTexture(matrices, TEXTURE, x + align + colon, y, 100, 11, 0, 0, 1F, 0.5F, color);
        drawTexture(matrices, TEXTURE, x + align + colon, y, (int) (ratio * 100), 11, 0, 0.5F, ratio, 1F, color);

        String text = getValString(data);
        int textWidth = textRenderer().getWidth(text);
        float textX = x + align + colon + Math.max((100 - textWidth) / 2F, 0F);
        float textY = y + 2;
        textRenderer().draw(matrices, text, textX, textY, 0xFFAAAAAA);
    }

}
