package badasintended.megane.runtime.component.block;

import badasintended.megane.api.provider.CauldronFluidProvider;
import badasintended.megane.runtime.registry.Registrar;
import java.util.List;
import mcp.mobius.waila.api.IBlockAccessor;
import mcp.mobius.waila.api.ITaggableList;
import mcp.mobius.waila.api.WailaConstants;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CauldronComponent extends FluidComponent {

    @Override
    protected void append(List<Text> tooltip, IBlockAccessor accessor) {
        List<CauldronFluidProvider> providers = Registrar.CAULDRON.get(accessor.getBlock());
        if (!providers.isEmpty()) {
            ((ITaggableList<Identifier, Text>) tooltip).setTag(WailaConstants.OBJECT_NAME_TAG, Blocks.CAULDRON.getName());
            CauldronFluidProvider provider = providers.get(0);
            BlockState state = accessor.getBlockState();
            addFluid(tooltip, accessor, DEFAULT_TAG, provider.getFluid(state), provider.getStored(state), provider.getMax(state));
        }
    }

}
