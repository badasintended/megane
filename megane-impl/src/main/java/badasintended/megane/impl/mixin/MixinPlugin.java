package badasintended.megane.impl.mixin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mcp.mobius.waila.utils.JsonConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public class MixinPlugin implements IMixinConfigPlugin {

    private static final Logger LOGGER = LogManager.getLogger("megane-impl-mixin");
    private static final String PREFIX = "badasintended.megane.impl.mixin.";
    private static final JsonConfig<Config> CONFIG = new JsonConfig<>("waila/megane-impl-mixin", Config.class);

    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (!mixinClassName.startsWith(PREFIX)) {
            LOGGER.error("[megane-impl-mixin] Invalid mixin class {}", mixinClassName);
            return false;
        }

        String entry = mixinClassName.substring(PREFIX.length());
        Map<String, Boolean> config = CONFIG.get().mixin;

        config.putIfAbsent(entry, true);

        boolean apply = config.get(entry);

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
