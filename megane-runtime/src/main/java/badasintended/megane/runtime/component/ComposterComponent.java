package badasintended.megane.runtime.component;

import badasintended.megane.runtime.MeganeWaila;
import badasintended.megane.util.MeganeUtils;
import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.RenderableTextComponent;
import net.minecraft.block.BlockState;
import net.minecraft.block.ComposterBlock;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;

import java.util.List;

public class ComposterComponent implements IComponentProvider {

    public static final ComposterComponent INSTANCE = new ComposterComponent();

    private static final CompoundTag TAG = new CompoundTag();

    static {
        TAG.putInt(MeganeUtils.key("color"), 0xFF915118);
    }

    @Override
    public void appendBody(List<Text> tooltip, IDataAccessor accessor, IPluginConfig config) {
        if (!MeganeUtils.config().fluid.isEnabled()) return;

        BlockState state = accessor.getBlockState();
        int level = state.get(ComposterBlock.LEVEL);
        TAG.putDouble(MeganeUtils.key("filled"), level);
        TAG.putDouble(MeganeUtils.key("max"), 7);
        TAG.putString(MeganeUtils.key("prefix"), I18n.translate("waila.megane.level"));
        TAG.putString(MeganeUtils.key("text"), String.valueOf(level));
        tooltip.add(new RenderableTextComponent(MeganeWaila.BAR, TAG));
    }

}
