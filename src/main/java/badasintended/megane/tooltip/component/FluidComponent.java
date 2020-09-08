package badasintended.megane.tooltip.component;

import badasintended.megane.Megane;
import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.RenderableTextComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;

import java.util.List;

import static badasintended.megane.MeganeUtils.CONFIG;
import static badasintended.megane.MeganeUtils.key;

public class FluidComponent implements IComponentProvider {

    public static final FluidComponent INSTANCE = new FluidComponent();

    private static final CompoundTag TAG = new CompoundTag();

    static {
        TAG.putBoolean(key("translate"), false);
        TAG.putInt(key("color"), 0xFF0D0D59);
        TAG.putString(key("unit"), "mB");
    }

    @Override
    public void appendBody(List<Text> tooltip, IDataAccessor accessor, IPluginConfig config) {
        if (!CONFIG.get().fluid.isEnabled()) return;

        CompoundTag data = accessor.getServerData();
        if (data.getBoolean(key("hasFluid"))) {
            boolean sneaking = accessor.getPlayer().isSneaking();

            for (int i = 0; i < data.getInt(key("fluidSlotCount")); i++) {

                double stored = data.getDouble(key("storedFluid" + i));
                if (stored == 0) continue;

                double max = data.getDouble(key("maxFluid" + i));

                TAG.putDouble(key("stored"), stored);
                TAG.putDouble(key("max"), max);
                TAG.putBoolean(key("verbose"), sneaking);
                TAG.putString(key("prefix"), data.getString(key("fluidName" + i)));

                tooltip.add(new RenderableTextComponent(Megane.BAR, TAG));
            }
        }
    }

}
