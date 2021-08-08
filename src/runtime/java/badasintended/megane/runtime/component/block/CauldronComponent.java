package badasintended.megane.runtime.component.block;

import java.util.List;

import badasintended.megane.api.provider.CauldronFluidProvider;
import badasintended.megane.runtime.registry.Registrar;
import mcp.mobius.waila.api.IBlockAccessor;
import mcp.mobius.waila.api.ITaggableList;
import mcp.mobius.waila.api.WailaConstants;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CauldronComponent extends FluidComponent {

    @Override
    protected void append(List<Text> tooltip, IBlockAccessor accessor) {
        BlockState state = accessor.getBlockState();
        List<CauldronFluidProvider> providers = Registrar.CAULDRON.get(state.getBlock());
        for (CauldronFluidProvider provider : providers) {
            provider.setupContext(accessor.getWorld(), accessor.getPlayer());
            if (provider.hasFluid(accessor.getBlockState())) {
                Fluid fluid = provider.getFluid(state);
                if (!fluid.matchesType(Fluids.EMPTY)) {
                    ((ITaggableList<Identifier, Text>) tooltip).setTag(
                        WailaConstants.OBJECT_NAME_TAG,
                        new LiteralText(String.format(accessor.getBlockNameFormat(), I18n.translate(Blocks.CAULDRON.getTranslationKey()))));
                    addFluid(tooltip, accessor, DEFAULT_TAG, fluid, provider.getStored(state), provider.getMax(state));
                    return;
                }
            }
        }
    }

}
