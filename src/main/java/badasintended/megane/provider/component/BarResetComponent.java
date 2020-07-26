package badasintended.megane.provider.component;

import badasintended.megane.Megane;
import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.RenderableTextComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;

import java.util.List;

import static badasintended.megane.MeganeUtils.key;

public class BarResetComponent implements IComponentProvider {

    public static final BarResetComponent INSTANCE = new BarResetComponent();

    private static final CompoundTag TAG = new CompoundTag();

    static {
        TAG.putBoolean(key("reset"), true);
    }

    private BarResetComponent() {
    }

    @Override
    public void appendBody(List<Text> tooltip, IDataAccessor accessor, IPluginConfig config) {
        tooltip.add(new RenderableTextComponent(Megane.BAR, TAG));
    }

}
