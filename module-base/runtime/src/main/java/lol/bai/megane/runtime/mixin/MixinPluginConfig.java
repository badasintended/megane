package lol.bai.megane.runtime.mixin;

import lol.bai.megane.runtime.config.MeganeConfig;
import lol.bai.megane.runtime.util.MeganeUtils;
import mcp.mobius.waila.config.PluginConfig;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PluginConfig.class)
public abstract class MixinPluginConfig {

    @Inject(method = "reload", at = @At("RETURN"), remap = false)
    private static void migrateConfig(CallbackInfo ci) {
        MeganeConfig config = MeganeUtils.config();
        if (config.isPluginConfigMigrated()) return;

        set(new Identifier("wailax:energy.enabled_block"), config.energy.isEnabled());
        set(new Identifier("wailax:fluid.enabled_block"), config.fluid.isEnabled());
        set(new Identifier("wailax:item.enabled_block"), config.inventory.isEnabled());
        set(new Identifier("wailax:item.enabled_entity"), config.entityInventory.isEnabled());
        set(new Identifier("wailax:progress.enabled_block"), config.progress.isEnabled());

        MeganeUtils.LOGGER.info("[megane] Migrated plugin config");

        save();
        config.setPluginConfigMigrated(true);
        MeganeUtils.CONFIG.save();
    }

    @Shadow
    public static <T> void set(Identifier key, T value) {
        throw new AssertionError();
    }

    @Shadow
    public static void save() {
        throw new AssertionError();
    }


}
