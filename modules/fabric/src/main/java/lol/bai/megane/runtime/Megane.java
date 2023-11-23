package lol.bai.megane.runtime;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import lol.bai.megane.api.MeganeModule;
import lol.bai.megane.runtime.registry.Registrar;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.VersionParsingException;
import net.fabricmc.loader.api.metadata.CustomValue;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.fabricmc.loader.api.metadata.version.VersionPredicate;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;

import static lol.bai.megane.runtime.util.MeganeUtils.LOGGER;
import static lol.bai.megane.runtime.util.MeganeUtils.MODULE_CONFIG;

@SuppressWarnings("unused")
public class Megane implements IWailaPlugin {

    private static final Class<Block> BLOCK = Block.class;
    private static final Class<LivingEntity> ENTITY = LivingEntity.class;

    @Override
    public void register(IRegistrar r) {
        // Modules
        FabricLoader loader = FabricLoader.getInstance();

        Registrar.INSTANCE.waila = r;

        loader.getAllMods().forEach(mod -> {
            ModMetadata metadata = mod.getMetadata();
            String modId = metadata.getId();
            if (metadata.containsCustomValue("megane:modules")) {
                metadata.getCustomValue("megane:modules").getAsArray().forEach(value -> {
                    boolean satisfied = true;
                    String className;
                    if (value.getType() == CustomValue.CvType.OBJECT) {
                        CustomValue.CvObject object = value.getAsObject();
                        className = object.get("init").getAsString();
                        if (object.containsKey("depends")) {
                            for (Map.Entry<String, CustomValue> dep : object.get("depends").getAsObject()) {
                                Optional<ModContainer> optional = loader.getModContainer(dep.getKey());
                                satisfied = optional.isPresent();
                                if (satisfied) {
                                    try {
                                        satisfied = VersionPredicate.parse(dep.getValue().getAsString()).test(optional.get().getMetadata().getVersion());
                                    } catch (VersionParsingException e) {
                                        LOGGER.error("Failed to parse dependency version for module " + className, e);
                                        satisfied = false;
                                    }
                                }
                                if (!satisfied) {
                                    break;
                                }
                            }
                        }
                    } else {
                        className = value.getAsString();
                    }
                    satisfied = satisfied && MODULE_CONFIG.get().modules
                        .computeIfAbsent(modId, s -> new HashMap<>())
                        .computeIfAbsent(className, s -> true);
                    MODULE_CONFIG.save();
                    if (satisfied)
                        try {
                            LOGGER.info("[megane] Loading {} from {}", className, modId);
                            MeganeModule entry = (MeganeModule) Class.forName(className).getDeclaredConstructor().newInstance();
                            entry.registerCommon(Registrar.INSTANCE);
                            if (loader.getEnvironmentType() == EnvType.CLIENT) {
                                entry.registerClient(Registrar.INSTANCE);
                            }
                        } catch (Throwable t) {
                            LOGGER.error("[megane] error when loading {} from {}", className, modId);
                            t.printStackTrace();
                        }
                });
            }
        });
    }

}
