package badasintended.megane.provider.component;

import badasintended.megane.PluginMegane;
import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.RenderableTextComponent;
import net.minecraft.block.BlockState;
import net.minecraft.block.CauldronBlock;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;

import java.util.List;

import static badasintended.megane.Utils.key;

public class CauldronComponent implements IComponentProvider {

    public static final CauldronComponent INSTANCE = new CauldronComponent();

    private static final CompoundTag TAG = new CompoundTag();

    static {
        TAG.putInt(key("color"), 0xFF0D0D59);
        TAG.putString(key("prefix"), "waila.megane.level");
        TAG.putBoolean(key("translate"), true);
    }

    private CauldronComponent() {
        super();
    }

    @Override
    public void appendBody(List<Text> tooltip, IDataAccessor accessor, IPluginConfig config) {
        if (config.get(PluginMegane.FLUID)) {
            BlockState state = accessor.getBlockState();
            int level = state.get(CauldronBlock.LEVEL);
            TAG.putDouble(key("filled"), level);
            TAG.putDouble(key("max"), 3);
            TAG.putString(key("text"), String.valueOf(level));
            tooltip.add(new RenderableTextComponent(PluginMegane.BAR, TAG));
        }
    }

}
