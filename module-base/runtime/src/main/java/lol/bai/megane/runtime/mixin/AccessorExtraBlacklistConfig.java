package lol.bai.megane.runtime.mixin;

import java.util.LinkedHashSet;

import mcp.mobius.waila.plugin.extra.config.ExtraBlacklistConfig;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ExtraBlacklistConfig.class)
public interface AccessorExtraBlacklistConfig {

    @Accessor
    LinkedHashSet<Identifier> getBlockIds();

    @Accessor
    LinkedHashSet<Identifier> getBlockEntityTypeIds();

    @Accessor
    LinkedHashSet<Identifier> getEntityTypeIds();

}
