package badasintended.megane.util;

import badasintended.megane.config.MeganeConfig;
import com.google.gson.GsonBuilder;
import com.mojang.blaze3d.systems.RenderSystem;
import mcp.mobius.waila.Waila;
import mcp.mobius.waila.utils.JsonConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.*;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public final class MeganeUtils {

    public static final String MODID = "megane";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public static final JsonConfig<MeganeConfig> CONFIG = new JsonConfig<>(Waila.MODID + "/" + MODID, MeganeConfig.class)
        .withGson(new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(Identifier.class, new Identifier.Serializer())
            .create()
        );

    private static final NavigableMap<Long, String> SUFFIXES = new TreeMap<>();

    static {
        SUFFIXES.put(1000L, "K");
        SUFFIXES.put(1000000L, "M");
        SUFFIXES.put(1000000000L, "G");
        SUFFIXES.put(1000000000000L, "T");
        SUFFIXES.put(1000000000000000L, "P");
        SUFFIXES.put(1000000000000000000L, "E");
    }

    public static String key(String key) {
        return MODID + ".data." + key;
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

    public static Identifier id(String path) {
        return new Identifier(MODID, path);
    }

    public static boolean hasMod(String id) {
        return FabricLoader.getInstance().isModLoaded(id);
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
        matrices.pop();
    }

    public static Text fluidName(Fluid fluid) {
        Identifier id = Registry.FLUID.getId(fluid);
        return new TranslatableText("block." + id.getNamespace() + "." + id.getPath());
    }

    @Environment(EnvType.CLIENT)
    public static void drawStack(ItemStack stack, int x, int y) {
        ItemRenderer item = MinecraftClient.getInstance().getItemRenderer();
        TextRenderer text = MinecraftClient.getInstance().textRenderer;

        item.renderInGui(stack, x, y);
        item.renderGuiItemOverlay(text, stack, x, y);
    }

    public static MeganeConfig config() {
        return CONFIG.get();
    }

    public static void reloadConfig() {
        CONFIG.invalidate();
        LOGGER.info("[megane] Loaded Config.");
    }

}
