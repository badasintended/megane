package lol.bai.megane.runtime;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import lol.bai.megane.api.MeganeModule;
import lol.bai.megane.runtime.data.block.BeaconData;
import lol.bai.megane.runtime.data.entity.StatusEffectData;
import lol.bai.megane.runtime.provider.block.BeaconComponentProvider;
import lol.bai.megane.runtime.provider.entity.PlayerHeadComponentProvider;
import lol.bai.megane.runtime.provider.entity.SpawnEggComponentProvider;
import lol.bai.megane.runtime.provider.entity.StatusEffectComponentProvider;
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
import net.minecraft.block.BeaconBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

import static lol.bai.megane.runtime.util.MeganeUtils.LOGGER;
import static lol.bai.megane.runtime.util.MeganeUtils.MODULE_CONFIG;
import static mcp.mobius.waila.api.TooltipPosition.BODY;

@SuppressWarnings("unused")
public class Megane implements IWailaPlugin {

    private static final Class<Block> BLOCK = Block.class;
    private static final Class<LivingEntity> ENTITY = LivingEntity.class;

    @Override
    public void register(IRegistrar r) {
        // --- BLOCK ---
        // Component
        r.addComponent(new BeaconComponentProvider(), BODY, BeaconBlock.class, Integer.MAX_VALUE);

        // Server Data
        r.addBlockData(new BeaconData(), BeaconBlock.class);

        // --- ENTITY ---
        r.addIcon(new SpawnEggComponentProvider(), ENTITY);
        r.addIcon(new PlayerHeadComponentProvider(), PlayerEntity.class);

        // Component
        r.addComponent(new StatusEffectComponentProvider(), BODY, ENTITY, Integer.MAX_VALUE);

        // Server Data
        r.addEntityData(new StatusEffectData(), ENTITY);

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
