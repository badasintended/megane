package badasintended.megane.runtime;

import java.io.File;
import java.nio.file.Path;

import badasintended.megane.api.MeganeModule;
import badasintended.megane.config.MeganeConfig;
import mcp.mobius.waila.Waila;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.metadata.CustomValue;
import net.fabricmc.loader.api.metadata.ModMetadata;

import static badasintended.megane.runtime.util.RuntimeUtils.oldConfigVersion;
import static badasintended.megane.runtime.util.RuntimeUtils.showUpdatedConfigToast;
import static badasintended.megane.util.MeganeUtils.CONFIG;
import static badasintended.megane.util.MeganeUtils.CONFIG_VERSION;
import static badasintended.megane.util.MeganeUtils.LOGGER;
import static badasintended.megane.util.MeganeUtils.MODID;
import static badasintended.megane.util.MeganeUtils.config;
import static badasintended.megane.util.MeganeUtils.hasMod;

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

        loader.getAllMods().forEach(mod -> {
            ModMetadata metadata = mod.getMetadata();
            String id = metadata.getId();
            if (metadata.containsCustomValue("megane:modules")) {
                metadata.getCustomValue("megane:modules").getAsArray().forEach(value -> {
                    boolean satisfied = true;
                    String className;
                    if (value.getType() == CustomValue.CvType.OBJECT) {
                        CustomValue.CvObject object = value.getAsObject();
                        className = object.get("init").getAsString();
                        if (object.containsKey("deps")) for (CustomValue dep : object.get("deps").getAsArray()) {
                            satisfied = satisfied && hasMod(dep.getAsString());
                        }
                    } else {
                        className = value.getAsString();
                    }
                    if (satisfied) try {
                        MeganeModule entry = (MeganeModule) Class.forName(className).newInstance();
                        entry.initialize();
                        if (loader.getEnvironmentType() == EnvType.CLIENT) entry.initializeClient();
                        LOGGER.info("[megane] Loaded {} from {}", className, id);
                    } catch (Exception e) {
                        //
                    }
                });
            }
        });
    }

}
