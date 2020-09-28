package badasintended.megane.runtime.renderer;

import mcp.mobius.waila.api.ICommonAccessor;
import mcp.mobius.waila.api.ITooltipRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import static badasintended.megane.util.MeganeUtils.*;

public class BarRenderer implements ITooltipRenderer {

    private static BarRenderer instance = null;

    private static final DecimalFormat FORMAT = new DecimalFormat("#.##");
    private static final Identifier TEXTURE = id("textures/bar.png");

    static {
        FORMAT.setRoundingMode(RoundingMode.DOWN);
    }

    public BarRenderer() {
        instance = this;
    }

    @Nullable
    public static BarRenderer getInstance() {
        return instance;
    }

    private int align = 0;

    private String getValString(CompoundTag data) {
        double stored = Math.max(data.getDouble(key("stored")), 0);
        double max = Math.max(data.getDouble(key("max")), 0);
        String unit = data.getString(key("unit"));
        boolean verbose = data.getBoolean(key("verbose"));

        String storedString;
        if (stored < 0 || stored == Double.MAX_VALUE) {
            storedString = "∞";
        } else {
            storedString = verbose ? String.valueOf(stored) : suffix((long) stored);
        }

        String maxString;
        if (max < 0 || max == Double.MAX_VALUE) {
            maxString = "∞";
        } else {
            maxString = verbose ? String.valueOf(max) : suffix((long) max);
        }

        return storedString + "/" + maxString + " " + unit;
    }

    public void resetAlign() {
        align = 0;
    }

    @Override
    public Dimension getSize(CompoundTag data, ICommonAccessor accessor) {
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        String prefix = data.getString(key("prefix"));
        if (data.getBoolean(key("translate"))) prefix = I18n.translate(prefix);
        prefix += ": ";
        int prefixWidth = textRenderer.getWidth(prefix);
        int textWidth = textRenderer.getWidth(getValString(data));
        align = Math.max(prefixWidth, align);
        return new Dimension(align + Math.max(textWidth, 100), 13);
    }

    @Override
    public void draw(MatrixStack matrices, CompoundTag data, ICommonAccessor accessor, int x, int y) {
        double stored = Math.max(data.getDouble(key("stored")), 0);
        double max = Math.max(data.getDouble(key("max")), 0);

        float ratio = max == 0 ? 1F : Math.min((float) (stored / max), 1F);

        int color = data.getInt(key("color"));

        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        String prefix = data.getString(key("prefix"));
        prefix += ": ";
        textRenderer.drawWithShadow(matrices, prefix, x, y + 2, 0xFFAAAAAA);

        drawTexture(matrices, TEXTURE, x + align, y, 100, 11, 0, 0, 1F, 0.5F, color);
        drawTexture(matrices, TEXTURE, x + align, y, (int) (ratio * 100), 11, 0, 0.5F, ratio, 1F, color);

        String text = getValString(data);
        int textWidth = textRenderer.getWidth(text);
        textRenderer.draw(matrices, text, x + align + Math.max((100 - textWidth) / 2F, 0F), y + 2, 0xFFAAAAAA);
    }

}
