package lol.bai.megane.runtime.mixin;

import java.lang.reflect.Type;
import java.util.Set;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import lol.bai.megane.runtime.config.MeganeConfig;
import lol.bai.megane.runtime.util.MeganeUtils;
import mcp.mobius.waila.plugin.extra.config.ExtraBlacklistConfig;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ExtraBlacklistConfig.Adapter.class)
public abstract class MixinExtraBlacklistConfigAdapter {

    @Unique
    private boolean megane_migrated = false;

    @Shadow
    @Final
    private Identifier tagId;

    @Inject(method = "deserialize(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lmcp/mobius/waila/plugin/extra/config/ExtraBlacklistConfig;", at = @At("RETURN"), remap = false)
    private void migrateBlacklist(JsonElement json, Type typeOfT, JsonDeserializationContext context, CallbackInfoReturnable<ExtraBlacklistConfig> cir) {
        if (megane_migrated) return;

        ExtraBlacklistConfig out = cir.getReturnValue();
        AccessorExtraBlacklistConfig access = (AccessorExtraBlacklistConfig) out;
        MeganeConfig config = MeganeUtils.config();

        if (tagId.equals(new Identifier("waila:extra/energy_blacklist"))) {
            add(out.blocks, access.getBlockIds(), Registry.BLOCK, config.energy.getBlacklist());
        } else if (tagId.equals(new Identifier("waila:extra/fluid_blacklist"))) {
            add(out.blocks, access.getBlockIds(), Registry.BLOCK, config.fluid.getBlacklist());
        } else if (tagId.equals(new Identifier("waila:extra/item_blacklist"))) {
            add(out.blocks, access.getBlockIds(), Registry.BLOCK, config.inventory.getBlacklist());
            add(out.entityTypes, access.getEntityTypeIds(), Registry.ENTITY_TYPE, config.entityInventory.getBlacklist());
        } else if (tagId.equals(new Identifier("waila:extra/progress_blacklist"))) {
            add(out.blocks, access.getBlockIds(), Registry.BLOCK, config.progress.getBlacklist());
        }

        MeganeUtils.LOGGER.info("[megane] migrated {}", tagId);
        megane_migrated = true;
    }

    @Unique
    private <T> void add(Set<T> out, Set<Identifier> outIds, Registry<T> registry, Set<Identifier> inIds) {
        for (Identifier inId : inIds) {
            outIds.add(inId);
            registry.getOrEmpty(inId).ifPresent(out::add);
        }
    }

}
