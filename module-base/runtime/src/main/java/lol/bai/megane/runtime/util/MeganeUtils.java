package lol.bai.megane.runtime.util;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import com.google.gson.GsonBuilder;
import com.mojang.blaze3d.systems.RenderSystem;
import lol.bai.megane.runtime.config.MeganeConfig;
import lol.bai.megane.runtime.config.ModuleConfig;
import mcp.mobius.waila.api.IJsonConfig;
import mcp.mobius.waila.api.WailaConstants;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class MeganeUtils {

    public static final String MODID = "megane";
    public static final String ISSUE_URL = "https://github.com/badasintended/megane/issues";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public static final int CONFIG_VERSION = 4;

    public static final IJsonConfig<MeganeConfig> CONFIG = IJsonConfig
        .of(MeganeConfig.class)
        .file(WailaConstants.WAILA + "/" + MODID)
        .gson(new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(Identifier.class, new Identifier.Serializer())
            .create())
        .version(CONFIG_VERSION, MeganeConfig::getConfigVersion, MeganeConfig::setConfigVersion)
        .build();

    public static final IJsonConfig<ModuleConfig> MODULE_CONFIG = IJsonConfig
        .of(ModuleConfig.class)
        .file(WailaConstants.WAILA + "/" + MODID + "_modules")
        .build();

    public static final NbtCompound EMPTY_TAG = new NbtCompound();

    private static final NavigableMap<Long, String> SUFFIXES = new TreeMap<>();
    private static final NavigableMap<Integer, String> ROMAN = new TreeMap<>();

    static {
        MeganeUtils.SUFFIXES.put(1000L, "K");
        MeganeUtils.SUFFIXES.put(1000000L, "M");
        MeganeUtils.SUFFIXES.put(1000000000L, "G");
        MeganeUtils.SUFFIXES.put(1000000000000L, "T");
        MeganeUtils.SUFFIXES.put(1000000000000000L, "P");
        MeganeUtils.SUFFIXES.put(1000000000000000000L, "E");

        MeganeUtils.ROMAN.put(1000, "M");
        MeganeUtils.ROMAN.put(900, "CM");
        MeganeUtils.ROMAN.put(500, "D");
        MeganeUtils.ROMAN.put(400, "CD");
        MeganeUtils.ROMAN.put(100, "C");
        MeganeUtils.ROMAN.put(90, "XC");
        MeganeUtils.ROMAN.put(50, "L");
        MeganeUtils.ROMAN.put(40, "XL");
        MeganeUtils.ROMAN.put(10, "X");
        MeganeUtils.ROMAN.put(9, "IX");
        MeganeUtils.ROMAN.put(5, "V");
        MeganeUtils.ROMAN.put(4, "IV");
        MeganeUtils.ROMAN.put(1, "I");
    }

    public static int oldConfigVersion = 0;
    public static boolean showUpdatedConfigToast = false;

    public static Identifier id(String path) {
        return id(MODID, path);
    }

    public static Identifier id(String namespace, String path) {
        return new Identifier(namespace, path);
    }

    public static MeganeConfig config() {
        return CONFIG.get();
    }

    public static void reloadConfig() {
        CONFIG.invalidate();
        LOGGER.info("[megane] Loaded Config.");
    }

    @Environment(EnvType.CLIENT)
    public static Text fluidName(Fluid fluid) {
        Identifier id = Registries.FLUID.getId(fluid);
        return Text.translatable("block." + id.getNamespace() + "." + id.getPath());
    }

    @Environment(EnvType.CLIENT)
    public static void drawTexture(
        MatrixStack matrices, Identifier id,
        int x, int y, int w, int h,
        float u0, float v0, float u1, float v1, int color
    ) {
        matrices.push();

        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionColorTexProgram);
        RenderSystem.setShaderTexture(0, id);

        int a = 0xFF;
        int r = getR(color);
        int g = getG(color);
        int b = getB(color);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE);

        buffer.vertex(matrices.peek().getPositionMatrix(), x, y + h, 0).color(r, g, b, a).texture(u0, v1).next();
        buffer.vertex(matrices.peek().getPositionMatrix(), x + w, y + h, 0).color(r, g, b, a).texture(u1, v1).next();
        buffer.vertex(matrices.peek().getPositionMatrix(), x + w, y, 0).color(r, g, b, a).texture(u1, v0).next();
        buffer.vertex(matrices.peek().getPositionMatrix(), x, y, 0).color(r, g, b, a).texture(u0, v0).next();

        tessellator.draw();
        RenderSystem.disableBlend();
        matrices.pop();
    }

    @Environment(EnvType.CLIENT)
    public static void drawStack(ItemStack stack, int x, int y) {
        ItemRenderer item = MinecraftClient.getInstance().getItemRenderer();
        TextRenderer text = MinecraftClient.getInstance().textRenderer;

        DiffuseLighting.enableGuiDepthLighting();
        RenderSystem.enableDepthTest();
        int count = stack.getCount();
        stack.setCount(1); // workaround for when item that technically unstackable but megane stacks them anyway
        item.renderInGui(stack, x, y);
        item.renderGuiItemOverlay(text, stack, x, y, count > 1 ? suffix(count) : "");
        stack.setCount(count); // restore original stack count for next frame
        DiffuseLighting.disableGuiDepthLighting();
        RenderSystem.disableDepthTest();
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
