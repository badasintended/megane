package badasintended.megane.mixin;

import net.fabricmc.fabric.impl.object.builder.BlockSettingsInternals;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractBlock.Settings.class)
public interface BlockSettingsAccessor extends BlockSettingsInternals {

    @Accessor
    Material getMaterial();

    @Accessor
    float getHardness();

    @Accessor
    boolean getToolRequired();

}
