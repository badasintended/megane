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

public class FluidComponent implements IComponentProvider {

    public static final FluidComponent INSTANCE = new FluidComponent();

    private static final CompoundTag TAG = new CompoundTag();

    static {
        TAG.putBoolean(key("translate"), false);
        TAG.putInt(key("color"), 0xFF0D0D59);
    }

    private FluidComponent() {
    }

    @Override
    public void appendBody(List<Text> tooltip, IDataAccessor accessor, IPluginConfig config) {
        if (config.get(Megane.FLUID)) {
            CompoundTag data = accessor.getServerData();
            if (data.getBoolean(key("hasFluid"))) {
                for (int i = 0; i < data.getInt(key("fluidSlotCount")); i++) {

                    double stored = data.getDouble(key("storedFluid" + i));
                    if (stored == 0) continue;

                    double max = data.getDouble(key("maxFluid" + i));

                    TAG.putDouble(key("stored"), stored);
                    TAG.putDouble(key("max"), max);
                    TAG.putString(key("text"), String.format("%s/%s B", stored, max));
                    TAG.putString(key("prefix"), data.getString(key("fluidName" + i)));

                    tooltip.add(new RenderableTextComponent(Megane.BAR, TAG));
                }
            }
        }
    }

}
