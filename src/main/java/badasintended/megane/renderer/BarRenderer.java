package badasintended.megane.renderer;

import mcp.mobius.waila.api.ICommonAccessor;
import mcp.mobius.waila.api.ITooltipRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;

import java.awt.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import static badasintended.megane.Utils.*;

@Environment(EnvType.CLIENT)
public class BarRenderer implements ITooltipRenderer {

    public static final BarRenderer INSTANCE = new BarRenderer();

    private static final DecimalFormat FORMAT = new DecimalFormat("#.##");
    private static final Identifier TEXTURE = id("textures/bar.png");
    private static final Dimension ZERO = new Dimension();

    static {
        FORMAT.setRoundingMode(RoundingMode.DOWN);
    }

    private int align = 0;

    private BarRenderer() {
    }

    private static double format(double n) {
        return Double.parseDouble(FORMAT.format(n));
    }

    @Override
    public Dimension getSize(CompoundTag data, ICommonAccessor accessor) {
        if (data.getBoolean(key("reset"))) {
            align = 0;
            return ZERO;
        }
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        String prefix = data.getString(key("prefix"));
        if (data.getBoolean(key("translate"))) prefix = I18n.translate(prefix);
        prefix += ": ";
        int prefixWidth = textRenderer.getWidth(prefix);
        int textWidth = textRenderer.getWidth(data.getString(key("text")));
        align = Math.max(prefixWidth, align);
        return new Dimension(align + Math.max(textWidth, 100), 13);
    }

    @Override
    public void draw(MatrixStack matrices, CompoundTag data, ICommonAccessor accessor, int x, int y) {
        if (data.getBoolean(key("reset"))) return;
        double filled = data.getDouble(key("filled"));
        double max = data.getDouble(key("max"));

        float ratio = max == 0 ? 1F : (float) format(filled / max);

        int color = data.getInt(key("color"));

        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        String prefix = data.getString(key("prefix"));
        if (data.getBoolean(key("translate"))) prefix = I18n.translate(prefix);
        prefix += ": ";
        textRenderer.drawWithShadow(matrices, prefix, x, y + 2, 0xFFAAAAAA);

        drawTexture(matrices, TEXTURE, x + align, y, 100, 11, 0, 0, 1F, 0.5F, color);
        drawTexture(matrices, TEXTURE, x + align, y, (int) (ratio * 100), 11, 0, 0.5F, ratio, 1F, color);

        String text = data.getString(key("text"));
        int textWidth = textRenderer.getWidth(text);
        textRenderer.draw(matrices, text, x + align + Math.max((100 - textWidth) / 2F, 0F), y + 2, 0xFFAAAAAA);
    }

}
