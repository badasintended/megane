package badasintended.megane.runtime.component;

import badasintended.megane.runtime.MeganeWaila;
import badasintended.megane.util.MeganeUtils;
import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.RenderableTextComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;

import java.util.List;

public class FluidComponent implements IComponentProvider {

    public static final FluidComponent INSTANCE = new FluidComponent();

    private static final CompoundTag TAG = new CompoundTag();

    static {
        TAG.putBoolean(MeganeUtils.key("translate"), false);
        TAG.putString(MeganeUtils.key("unit"), "mB");
    }

    @Override
    public void appendBody(List<Text> tooltip, IDataAccessor accessor, IPluginConfig config) {
        if (!MeganeUtils.config().fluid.isEnabled()) return;

        CompoundTag data = accessor.getServerData();
        if (data.getBoolean(MeganeUtils.key("hasFluid"))) {
            boolean expand = accessor.getPlayer().isSneaking() && MeganeUtils.config().fluid.isExpandWhenSneak();

            for (int i = 0; i < data.getInt(MeganeUtils.key("fluidSlotCount")); i++) {

                double stored = data.getDouble(MeganeUtils.key("storedFluid" + i));
                if (stored == 0) continue;

                double max = data.getDouble(MeganeUtils.key("maxFluid" + i));

                TAG.putInt(MeganeUtils.key("color"), MeganeUtils.config().fluid.getBarColor());
                TAG.putDouble(MeganeUtils.key("stored"), stored);
                TAG.putDouble(MeganeUtils.key("max"), max);
                TAG.putBoolean(MeganeUtils.key("verbose"), expand);
                TAG.putString(MeganeUtils.key("prefix"), data.getString(MeganeUtils.key("fluidName" + i)));

                tooltip.add(new RenderableTextComponent(MeganeWaila.BAR, TAG));
            }
        }
    }

}
