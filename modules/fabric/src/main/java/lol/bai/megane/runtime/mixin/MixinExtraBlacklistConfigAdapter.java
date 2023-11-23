package lol.bai.megane.runtime.mixin;

import java.lang.reflect.Type;
import java.util.Set;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import lol.bai.megane.runtime.config.MeganeConfig;
import lol.bai.megane.runtime.util.MeganeUtils;
import mcp.mobius.waila.plugin.extra.config.ExtraBlacklistConfig;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ExtraBlacklistConfig.Adapter.class)
public abstract class MixinExtraBlacklistConfigAdapter {

    @Unique
    private ResourceLocation megane_tagId;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void storeTagId(ResourceLocation tagId, CallbackInfo ci) {
        megane_tagId = tagId;
    }

    @Inject(method = "deserialize(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lmcp/mobius/waila/plugin/extra/config/ExtraBlacklistConfig;", at = @At("RETURN"), remap = false)
    private void migrateBlacklist(JsonElement json, Type typeOfT, JsonDeserializationContext context, CallbackInfoReturnable<ExtraBlacklistConfig> cir) {
        MeganeConfig config = MeganeUtils.config();
        if (config.getMigratedBlacklist().contains(megane_tagId)) return;

        ExtraBlacklistConfig out = cir.getReturnValue();

        if (megane_tagId.equals(new ResourceLocation("waila:extra/energy_blacklist"))) {
            add(out.blocks, config.energy.getBlacklist());
        } else if (megane_tagId.equals(new ResourceLocation("waila:extra/fluid_blacklist"))) {
            add(out.blocks, config.fluid.getBlacklist());
        } else if (megane_tagId.equals(new ResourceLocation("waila:extra/item_blacklist"))) {
            add(out.blocks, config.inventory.getBlacklist());
            add(out.entityTypes, config.entityInventory.getBlacklist());
        } else if (megane_tagId.equals(new ResourceLocation("waila:extra/progress_blacklist"))) {
            add(out.blocks, config.progress.getBlacklist());
        }

        MeganeUtils.LOGGER.info("[megane] migrated {}", megane_tagId);
        config.getMigratedBlacklist().add(megane_tagId);
        MeganeUtils.CONFIG.save();
    }

    @Unique
    private void add(Set<String> out, Set<ResourceLocation> inIds) {
        for (ResourceLocation inId : inIds) {
            out.add(inId.toString());
        }
    }

}
