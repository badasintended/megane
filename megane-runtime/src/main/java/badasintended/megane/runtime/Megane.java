package badasintended.megane.runtime;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;

import badasintended.megane.api.MeganeModule;
import badasintended.megane.config.MeganeConfig;
import badasintended.megane.runtime.component.AlignResetComponent;
import badasintended.megane.runtime.component.block.BeaconComponent;
import badasintended.megane.runtime.component.block.BeeHiveComponent;
import badasintended.megane.runtime.component.block.BlockInventoryComponent;
import badasintended.megane.runtime.component.block.CauldronComponent;
import badasintended.megane.runtime.component.block.ComposterComponent;
import badasintended.megane.runtime.component.block.EnergyComponent;
import badasintended.megane.runtime.component.block.FluidComponent;
import badasintended.megane.runtime.component.block.ProgressComponent;
import badasintended.megane.runtime.component.entity.EntityInventoryComponent;
import badasintended.megane.runtime.component.entity.PetOwnerComponent;
import badasintended.megane.runtime.component.entity.PlayerHeadComponent;
import badasintended.megane.runtime.component.entity.SpawnEggComponent;
import badasintended.megane.runtime.component.entity.StatusEffectComponent;
import badasintended.megane.runtime.data.block.BeaconData;
import badasintended.megane.runtime.data.block.BlockInventoryData;
import badasintended.megane.runtime.data.block.EnergyData;
import badasintended.megane.runtime.data.block.FluidData;
import badasintended.megane.runtime.data.block.ProgressData;
import badasintended.megane.runtime.data.entity.EntityInventoryData;
import badasintended.megane.runtime.data.entity.PetOwnerData;
import badasintended.megane.runtime.data.entity.StatusEffectData;
import badasintended.megane.runtime.registry.Registrar;
import badasintended.megane.runtime.renderer.AlignedTextRenderer;
import badasintended.megane.runtime.renderer.BarRenderer;
import badasintended.megane.runtime.renderer.InventoryRenderer;
import badasintended.megane.runtime.renderer.ProgressRenderer;
import badasintended.megane.runtime.renderer.StatusEffectRenderer;
import mcp.mobius.waila.Waila;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IServerDataProvider;
import mcp.mobius.waila.api.IWailaPlugin;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.metadata.CustomValue;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.block.BeaconBlock;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.Block;
import net.minecraft.block.CauldronBlock;
import net.minecraft.block.ComposterBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

import static badasintended.megane.runtime.util.RuntimeUtils.oldConfigVersion;
import static badasintended.megane.runtime.util.RuntimeUtils.showUpdatedConfigToast;
import static badasintended.megane.util.MeganeUtils.CONFIG;
import static badasintended.megane.util.MeganeUtils.CONFIG_VERSION;
import static badasintended.megane.util.MeganeUtils.LOGGER;
import static badasintended.megane.util.MeganeUtils.MODID;
import static badasintended.megane.util.MeganeUtils.config;
import static badasintended.megane.util.MeganeUtils.hasMod;
import static badasintended.megane.util.MeganeUtils.id;
import static mcp.mobius.waila.api.TooltipPosition.HEAD;
import static mcp.mobius.waila.api.TooltipPosition.TAIL;

public class Megane implements IWailaPlugin {

    public static final Identifier INVENTORY = id("inventory");
    public static final Identifier BAR = id("bar");
    public static final Identifier PROGRESS = id("progress");
    public static final Identifier ALIGNED = id("aligned");
    public static final Identifier EFFECT = id("effect");

    private static final Class<Block> BLOCK = Block.class;
    private static final Class<LivingEntity> ENTITY = LivingEntity.class;

    @Override
    public void register(IRegistrar r) {
        // Renderer
        r.registerTooltipRenderer(INVENTORY, new InventoryRenderer());
        r.registerTooltipRenderer(BAR, new BarRenderer());
        r.registerTooltipRenderer(PROGRESS, new ProgressRenderer());
        r.registerTooltipRenderer(ALIGNED, new AlignedTextRenderer());
        r.registerTooltipRenderer(EFFECT, new StatusEffectRenderer());

        // --- BLOCK ---
        // Component
        r.registerComponentProvider(new AlignResetComponent.Block(), HEAD, BLOCK);
        r.registerComponentProvider(new EnergyComponent(), HEAD, BLOCK);
        r.registerComponentProvider(new FluidComponent(), HEAD, BLOCK);
        r.registerComponentProvider(new CauldronComponent(), HEAD, CauldronBlock.class);
        r.registerComponentProvider(new ComposterComponent(), HEAD, ComposterBlock.class);
        r.registerComponentProvider(new BeeHiveComponent(), HEAD, BeehiveBlock.class);

        r.registerComponentProvider(new BlockInventoryComponent(), TAIL, BLOCK);
        r.registerComponentProvider(new ProgressComponent(), TAIL, BLOCK);
        r.registerComponentProvider(new BeaconComponent(), TAIL, BeaconBlock.class);


        // Server Data
        r.registerBlockDataProvider(new BlockInventoryData(), BLOCK);
        r.registerBlockDataProvider(new EnergyData(), BLOCK);
        r.registerBlockDataProvider(new FluidData(), BLOCK);
        r.registerBlockDataProvider(new ProgressData(), BLOCK);
        r.registerBlockDataProvider(new BeaconData(), BeaconBlock.class);

        // --- ENTITY ---
        r.registerEntityStackProvider(new SpawnEggComponent(), ENTITY);
        r.registerEntityStackProvider(new PlayerHeadComponent(), PlayerEntity.class);

        // Component
        r.registerComponentProvider(new AlignResetComponent.Entity(), HEAD, ENTITY);
        r.registerComponentProvider(new PetOwnerComponent(), HEAD, ENTITY);

        r.registerComponentProvider(new EntityInventoryComponent(), TAIL, ENTITY);
        r.registerComponentProvider(new StatusEffectComponent(), TAIL, ENTITY);

        // Server Data
        r.registerEntityDataProvider((IServerDataProvider) new EntityInventoryData(), ENTITY);
        r.registerEntityDataProvider((IServerDataProvider) new PetOwnerData(), ENTITY);
        r.registerEntityDataProvider((IServerDataProvider) new StatusEffectData(), ENTITY);

        // Modules
        FabricLoader loader = FabricLoader.getInstance();

        Path conf = loader.getConfigDir();
        File file = conf.resolve(Waila.MODID + "/" + MODID + ".json").toFile();
        if (file.exists()) {
            int version = config().configVersion;
            if (version != CONFIG_VERSION)
                try {
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
            String modId = metadata.getId();
            if (metadata.containsCustomValue("megane:modules")) {
                metadata.getCustomValue("megane:modules").getAsArray().forEach(value -> {
                    boolean satisfied = true;
                    String className;
                    if (value.getType() == CustomValue.CvType.OBJECT) {
                        CustomValue.CvObject object = value.getAsObject();
                        className = object.get("init").getAsString();
                        if (object.containsKey("deps"))
                            for (CustomValue dep : object.get("deps").getAsArray()) {
                                satisfied = satisfied && hasMod(dep.getAsString());
                            }
                    } else {
                        className = value.getAsString();
                    }
                    satisfied = satisfied && config().modules
                        .computeIfAbsent(modId, s -> new HashMap<>())
                        .computeIfAbsent(className, s -> true);
                    CONFIG.save();
                    if (satisfied)
                        try {
                            LOGGER.info("[megane] Loading {} from {}", className, modId);
                            MeganeModule entry = (MeganeModule) Class.forName(className).getDeclaredConstructor().newInstance();
                            entry.register(Registrar.INSTANCE);
                            if (loader.getEnvironmentType() == EnvType.CLIENT)
                                entry.registerClient(Registrar.INSTANCE);
                        } catch (Exception e) {
                            LOGGER.error("[megane] error when loading {} from {}", className, modId);
                            e.printStackTrace();
                        }
                });
            }
        });
    }

}
