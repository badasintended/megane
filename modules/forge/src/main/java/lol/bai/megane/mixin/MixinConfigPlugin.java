package lol.bai.megane.mixin;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraftforge.fml.loading.FMLLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public class MixinConfigPlugin implements IMixinConfigPlugin {

    private static final Map<String, String> MAP = Map.of(
        "ae2", "ae2",
        "alloyforgery", "alloy_forgery",
        "create", "create",
        "moderndynamics", "moderndynamics"
    );

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        var split = mixinClassName.replace('/', '.').split("\\.");
        var packageName = split[split.length - 2];

        if (MAP.containsKey(packageName)) {
            return FMLLoader.getLoadingModList().getModFileById(MAP.get(packageName)) != null;
        } else {
            return true;
        }
    }

    //

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
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

}
