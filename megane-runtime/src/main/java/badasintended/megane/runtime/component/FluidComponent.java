package badasintended.megane.runtime.component;

import badasintended.megane.runtime.MeganeWaila;
import mcp.mobius.waila.api.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;

import java.util.List;

import static badasintended.megane.util.MeganeUtils.config;
import static badasintended.megane.util.MeganeUtils.key;

public class FluidComponent implements IComponentProvider {

    public static final FluidComponent INSTANCE = new FluidComponent();

    private static final CompoundTag TAG = new CompoundTag();

    static {
        TAG.putBoolean(key("translate"), false);
        TAG.putString(key("unit"), "mB");
    }

    @Override
    public void appendBody(List<Text> tooltip, IDataAccessor accessor, IPluginConfig config) {
        if (!config().fluid.isEnabled()) return;

        CompoundTag data = accessor.getServerData();
        if (data.getBoolean(key("hasFluid"))) {
            boolean expand = accessor.getPlayer().isSneaking() && config().fluid.isExpandWhenSneak();

            for (int i = 0; i < data.getInt(key("fluidSlotCount")); i++) {

                double stored = data.getDouble(key("storedFluid" + i));
                if (stored == 0) continue;

                double max = data.getDouble(key("maxFluid" + i));

                TAG.putInt(key("color"), config().fluid.getBarColor());
                TAG.putDouble(key("stored"), stored);
                TAG.putDouble(key("max"), max);
                TAG.putBoolean(key("verbose"), expand);
                TAG.putString(key("prefix"), data.getString(key("fluidName" + i)));

                tooltip.add(new RenderableTextComponent(MeganeWaila.BAR, TAG));
            }
        }
    }

}
