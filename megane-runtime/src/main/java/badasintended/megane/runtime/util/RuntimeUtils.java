package badasintended.megane.runtime.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.*;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.*;

public class RuntimeUtils {

    public static final NavigableMap<Long, String> SUFFIXES = new TreeMap<>();

    static {
        SUFFIXES.put(1000L, "K");
        SUFFIXES.put(1000000L, "M");
        SUFFIXES.put(1000000000L, "G");
        SUFFIXES.put(1000000000000L, "T");
        SUFFIXES.put(1000000000000000L, "P");
        SUFFIXES.put(1000000000000000000L, "E");
    }

    @Environment(EnvType.CLIENT)
    public static void drawTexture(
        MatrixStack matrices, Identifier id,
        int x, int y, int w, int h,
        float u0, float v0, float u1, float v1, int color
    ) {
        matrices.push();
        RenderSystem.enableBlend();
        MinecraftClient.getInstance().getTextureManager().bindTexture(id);

        int a = getA(color);
        int r = getR(color);
        int g = getG(color);
        int b = getB(color);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        buffer.begin(7, VertexFormats.POSITION_COLOR_TEXTURE);

        buffer.vertex(matrices.peek().getModel(), x, y + h, 0).color(r, g, b, a).texture(u0, v1).next();
        buffer.vertex(matrices.peek().getModel(), x + w, y + h, 0).color(r, g, b, a).texture(u1, v1).next();
        buffer.vertex(matrices.peek().getModel(), x + w, y, 0).color(r, g, b, a).texture(u1, v0).next();
        buffer.vertex(matrices.peek().getModel(), x, y, 0).color(r, g, b, a).texture(u0, v0).next();

        tessellator.draw();
        RenderSystem.disableBlend();
        matrices.pop();
    }

    @Environment(EnvType.CLIENT)
    public static void drawStack(ItemStack stack, int x, int y) {
        ItemRenderer item = MinecraftClient.getInstance().getItemRenderer();
        TextRenderer text = MinecraftClient.getInstance().textRenderer;

        item.renderInGui(stack, x, y);
        item.renderGuiItemOverlay(text, stack, x, y);
    }

    public static int getA(int aarrggbb) {
        return (aarrggbb >> 24) & 0xFF;
    }

    public static int getR(int aarrggbb) {
        return (aarrggbb >> 16) & 0xFF;
    }

    public static int getG(int aarrggbb) {
        return (aarrggbb >> 8) & 0xFF;
    }

    public static int getB(int aarrggbb) {
        return aarrggbb & 0xFF;
    }

    public static boolean isDark(int aarrggbb) {
        return 1 - (0.299 * getR(aarrggbb) + 0.587 * getG(aarrggbb) + 0.114 * getB(aarrggbb)) / 255 > 0.5;
    }

    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    public static String suffix(long value) {
        if (value == Long.MIN_VALUE) return suffix(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + suffix(-value);
        if (value < 1000) return Long.toString(value);

        Map.Entry<Long, String> e = SUFFIXES.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();
        long truncated = value / (divideBy / 10);
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }

}
