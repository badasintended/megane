package badasintended.megane.impl.mixin;

import java.io.BufferedReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import mcp.mobius.waila.utils.JsonConfig;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.SemanticVersion;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.VersionParsingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public class MixinPlugin implements IMixinConfigPlugin {

    private static final Logger LOGGER = LogManager.getLogger("megane-impl-mixin");
    private static final String PREFIX = "badasintended.megane.impl.mixin.";
    private static final JsonConfig<Config> CONFIG = new JsonConfig<>(
        FabricLoader.getInstance().getConfigDir().resolve("waila/megane-impl-mixin.json").toFile(),
        Config.class
    );

    private static final Map<String, Map<String, Map<String, String>>> DEPENDENCIES = new HashMap<>();

    static {
        Type type = new TypeToken<Map<String, Map<String, Map<String, String>>>>() {
        }.getType();
        Gson gson = new Gson();
        for (ModContainer mod : FabricLoader.getInstance().getAllMods()) {
            Path path = mod.getPath("mixindependencies.json");
            try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
                Map<String, Map<String, Map<String, String>>> map = gson.fromJson(reader, type);
                DEPENDENCIES.putAll(map);
            } catch (Exception ex) {
                // no-op
            }
        }
    }

    private Map<String, Map<String, String>> mixinDependencies;
    private String mixinPackage;

    @Override
    public void onLoad(String mixinPackage) {
        this.mixinDependencies = DEPENDENCIES.getOrDefault(mixinPackage.substring(PREFIX.length()), Object2ObjectMaps.emptyMap());
        this.mixinPackage = mixinPackage;
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        String entry = mixinClassName.substring(PREFIX.length());
        Map<String, Boolean> config = CONFIG.get().mixin;

        config.putIfAbsent(entry, true);

        boolean apply = config.get(entry);

        if (apply) {
            try {
                String key = mixinClassName.substring(mixinPackage.length() + 1);
                if (mixinDependencies.containsKey(key)) {
                    Map<String, String> dependencies = mixinDependencies.get(key);
                    for (Map.Entry<String, String> dependency : dependencies.entrySet()) {
                        Optional<ModContainer> mod = FabricLoader.getInstance().getModContainer(dependency.getKey());
                        if (mod.isPresent()) {
                            Version modVersion = mod.get().getMetadata().getVersion();
                            Version depVersion = Version.parse(dependency.getValue());

                            if (depVersion instanceof SemanticVersion && modVersion instanceof SemanticVersion) {
                                apply = ((SemanticVersion) depVersion).compareTo((SemanticVersion) modVersion) <= 0;
                            } else if (!(depVersion.toString().equals("*") || depVersion.toString().equals(modVersion.toString()))) {
                                apply = false;
                            }
                        } else {
                            apply = false;
                        }
                        if (!apply) {
                            break;
                        }
                    }
                }
            } catch (VersionParsingException e) {
                apply = false;
                e.printStackTrace();
            }
        }

        LOGGER.info("[megane-impl-mixin] {}abling {}", apply ? "En" : "Dis", mixinClassName);

        return apply;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
        CONFIG.save();
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    public static class Config {

        public final Map<String, Boolean> mixin = new HashMap<>();

    }

}
