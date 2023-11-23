package lol.bai.megane.runtime.util;

import java.util.NavigableMap;
import java.util.TreeMap;

import com.google.gson.GsonBuilder;
import lol.bai.megane.runtime.config.MeganeConfig;
import lol.bai.megane.runtime.config.ModuleConfig;
import mcp.mobius.waila.api.IJsonConfig;
import mcp.mobius.waila.api.WailaConstants;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.resources.ResourceLocation;
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
            .registerTypeAdapter(ResourceLocation.class, new ResourceLocation.Serializer())
            .create())
        .version(CONFIG_VERSION, MeganeConfig::getConfigVersion, MeganeConfig::setConfigVersion)
        .build();

    public static final IJsonConfig<ModuleConfig> MODULE_CONFIG = IJsonConfig
        .of(ModuleConfig.class)
        .file(WailaConstants.WAILA + "/" + MODID + "_modules")
        .build();

    public static int oldConfigVersion = 0;
    public static boolean showUpdatedConfigToast = false;

    public static ResourceLocation id(String path) {
        return id(MODID, path);
    }

    public static ResourceLocation id(String namespace, String path) {
        return new ResourceLocation(namespace, path);
    }

    public static MeganeConfig config() {
        return CONFIG.get();
    }

    public static void reloadConfig() {
        CONFIG.invalidate();
        LOGGER.info("[megane] Loaded Config.");
    }

}
