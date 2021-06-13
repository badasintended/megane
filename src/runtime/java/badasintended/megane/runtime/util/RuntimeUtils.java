package badasintended.megane.runtime.util;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RuntimeUtils {

    public static final NbtCompound EMPTY_TAG = new NbtCompound();

    private static final NavigableMap<Long, String> SUFFIXES = new TreeMap<>();
    private static final NavigableMap<Integer, String> ROMAN = new TreeMap<>();

    public static int oldConfigVersion = 0;
    public static boolean showUpdatedConfigToast = false;

    public static int align = 0;

    static {
        SUFFIXES.put(1000L, "K");
        SUFFIXES.put(1000000L, "M");
        SUFFIXES.put(1000000000L, "G");
        SUFFIXES.put(1000000000000L, "T");
        SUFFIXES.put(1000000000000000L, "P");
        SUFFIXES.put(1000000000000000000L, "E");

        ROMAN.put(1000, "M");
        ROMAN.put(900, "CM");
        ROMAN.put(500, "D");
        ROMAN.put(400, "CD");
        ROMAN.put(100, "C");
        ROMAN.put(90, "XC");
        ROMAN.put(50, "L");
        ROMAN.put(40, "XL");
        ROMAN.put(10, "X");
        ROMAN.put(9, "IX");
        ROMAN.put(5, "V");
        ROMAN.put(4, "IV");
        ROMAN.put(1, "I");
    }

    @Environment(EnvType.CLIENT)
    public static String fluidName(Fluid fluid) {
        Identifier id = Registry.FLUID.getId(fluid);
        return I18n.translate("block." + id.getNamespace() + "." + id.getPath());
    }

    @Environment(EnvType.CLIENT)
    public static void drawTexture(
        MatrixStack matrices, Identifier id,
        int x, int y, int w, int h,
        float u0, float v0, float u1, float v1, int color
    ) {
        matrices.push();

        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
        RenderSystem.setShaderTexture(0, id);

        int a = 0xFF;
        int r = getR(color);
        int g = getG(color);
        int b = getB(color);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE);

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

    public static int getR(int aarrggbb) {
        return (aarrggbb >> 16) & 0xFF;
    }

    public static int getG(int aarrggbb) {
        return (aarrggbb >> 8) & 0xFF;
    }

    public static int getB(int aarrggbb) {
        return aarrggbb & 0xFF;
    }

    public static double getBrightness(int color) {
        return (0.299 * getR(color) + 0.587 * getG(color) + 0.114 * getB(color)) / 255.0;
    }

    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    public static String suffix(long value) {
        if (value == Long.MIN_VALUE)
            return suffix(Long.MIN_VALUE + 1);
        if (value < 0)
            return "-" + suffix(-value);
        if (value < 1000)
            return Long.toString(value);

        Map.Entry<Long, String> e = SUFFIXES.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();
        long truncated = value / (divideBy / 10);
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }

    public static String toRoman(int value) {
        if (value >= 4000)
            return String.valueOf(value);
        int l = ROMAN.floorKey(value);
        if (value == l) {
            return ROMAN.get(value);
        }
        return ROMAN.get(l) + toRoman(value - l);
    }

    @Environment(EnvType.CLIENT)
    public static TextRenderer textRenderer() {
        return MinecraftClient.getInstance().textRenderer;
    }

}
