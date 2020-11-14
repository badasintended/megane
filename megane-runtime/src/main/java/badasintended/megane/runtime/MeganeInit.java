package badasintended.megane.runtime;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;

import badasintended.megane.api.MeganeEntrypoint;
import badasintended.megane.config.MeganeConfig;
import badasintended.megane.util.MeganeUtils;
import mcp.mobius.waila.Waila;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import static badasintended.megane.runtime.util.RuntimeUtils.oldConfigVersion;
import static badasintended.megane.runtime.util.RuntimeUtils.showUpdatedConfigToast;
import static badasintended.megane.util.MeganeUtils.CONFIG;
import static badasintended.megane.util.MeganeUtils.CONFIG_VERSION;
import static badasintended.megane.util.MeganeUtils.LOGGER;
import static badasintended.megane.util.MeganeUtils.MODID;
import static badasintended.megane.util.MeganeUtils.config;

public class MeganeInit implements ModInitializer {

    @Override
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void onInitialize() {
        FabricLoader loader = FabricLoader.getInstance();

        Path conf = loader.getConfigDir();
        File file = conf.resolve(Waila.MODID + "/" + MODID + ".json").toFile();
        if (file.exists()) {
            int version = config().configVersion;
            if (version != CONFIG_VERSION) try {
                File old = conf.resolve(Waila.MODID + "/" + MODID + ".json.old").normalize().toFile();
                old.delete();
                file.renameTo(old);

                MeganeConfig config = new MeganeConfig();
                config.configVersion = CONFIG_VERSION;
                CONFIG.write(config, true);

                LOGGER.warn(
                    "[megane] Config reset because of different version ({} instead of {}), old config is available at {}",
                    version, CONFIG_VERSION, old
                );
                oldConfigVersion = version;
                showUpdatedConfigToast = true;
            } catch (Exception e) {
                // no-op
            }
        } else {
            config().configVersion = CONFIG_VERSION;
            CONFIG.save();
        }

        loader.getEntrypointContainers("megane", MeganeEntrypoint.class).forEach(val -> {
            MeganeEntrypoint entry = val.getEntrypoint();
            String[] deps = entry.dependencies();
            boolean satisfied = deps.length == 0 || Arrays.stream(deps).allMatch(MeganeUtils::hasMod);
            String className = entry.getClass().getName();
            String id = val.getProvider().getMetadata().getId();
            if (satisfied) {
                entry.initialize();
                if (loader.getEnvironmentType() == EnvType.CLIENT) entry.initializeClient();
                LOGGER.info("[megane] Loaded {} from {}", className, id);
            } else {
                LOGGER.warn("[megane] {} from {} needs {} to load", className, id, deps);
            }
        });
    }

}
