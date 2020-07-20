package badasintended.megane;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public final class Utils {

    public static final String MODID = "megane";

    private static final NavigableMap<Long, String> SUFFIXES = new TreeMap<>();

    static {
        SUFFIXES.put(1000L, "K");
        SUFFIXES.put(1000000L, "M");
        SUFFIXES.put(1000000000L, "G");
        SUFFIXES.put(1000000000000L, "T");
        SUFFIXES.put(1000000000000000L, "P");
        SUFFIXES.put(1000000000000000000L, "E");
    }

    public static MutableText tl(String key, Object... args) {
        return new TranslatableText("waila.megane." + key, args);
    }

    public static MutableText format(String text, Formatting... format) {
        return format(new LiteralText(text), format);
    }

    public static MutableText format(MutableText text, Formatting... format) {
        return text.setStyle(Style.EMPTY.withFormatting(format));
    }

    public static String key(String key) {
        return MODID + ".data." + key;
    }

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

    public static Identifier id(String path) {
        return new Identifier(MODID, path);
    }

    public static boolean hasMod(String id) {
        return FabricLoader.getInstance().isModLoaded(id);
    }

    public static void drawTexture(
        MatrixStack matrices, Identifier id,
        int x, int y, int w, int h,
        float u0, float v0, float u1, float v1, int color
    ) {
        RenderSystem.enableBlend();

        TextureManager textureManager = MinecraftClient.getInstance().getTextureManager();
        textureManager.bindTexture(id);

        int a = (color & 0xFF000000) >> 24;
        int r = (color & 0xFF0000) >> 16;
        int g = (color & 0xFF00) >> 8;
        int b = (color & 0xFF);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        buffer.begin(7, VertexFormats.POSITION_COLOR_TEXTURE);

        buffer.vertex(matrices.peek().getModel(), x, y + h, 0).color(r, g, b, a).texture(u0, v1).next();
        buffer.vertex(matrices.peek().getModel(), x + w, y + h, 0).color(r, g, b, a).texture(u1, v1).next();
        buffer.vertex(matrices.peek().getModel(), x + w, y, 0).color(r, g, b, a).texture(u1, v0).next();
        buffer.vertex(matrices.peek().getModel(), x, y, 0).color(r, g, b, a).texture(u0, v0).next();

        tessellator.draw();
        RenderSystem.disableBlend();
    }

}
