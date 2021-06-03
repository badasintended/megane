package badasintended.megane.util;

import badasintended.megane.config.MeganeConfig;
import com.google.gson.GsonBuilder;
import mcp.mobius.waila.Waila;
import mcp.mobius.waila.utils.JsonConfig;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.fluid.Fluid;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class MeganeUtils {

    private static final int[] EMPTY_INT_ARRAY = new int[0];

    public static final String MODID = "megane";
    public static final String ISSUE_URL = "https://github.com/badasintended/megane/issues";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public static final int CONFIG_VERSION = 2;

    public static final JsonConfig<MeganeConfig> CONFIG = new JsonConfig<>(Waila.MODID + "/" + MODID, MeganeConfig.class)
        .withGson(new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(Identifier.class, new Identifier.Serializer())
            .create()
        );

    public static Identifier id(String path) {
        return id(MODID, path);
    }

    public static Identifier id(String namespace, String path) {
        return new Identifier(namespace, path);
    }

    public static boolean hasMod(String id) {
        return FabricLoader.getInstance().isModLoaded(id);
    }

    public static Text fluidName(Fluid fluid) {
        Identifier id = Registry.FLUID.getId(fluid);
        return new TranslatableText("block." + id.getNamespace() + "." + id.getPath());
    }

    public static MeganeConfig config() {
        return CONFIG.get();
    }

    public static void reloadConfig() {
        CONFIG.invalidate();
        LOGGER.info("[megane] Loaded Config.");
    }

    public static int[] intRange(int start, int end) {
        if (end < start) {
            return EMPTY_INT_ARRAY;
        }

        int[] range = new int[end - start];

        for (int i = start; i < end; i++) {
            range[i - start] = i;
        }

        return range;
    }

}
