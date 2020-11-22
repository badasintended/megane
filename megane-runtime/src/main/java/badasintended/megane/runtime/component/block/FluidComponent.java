package badasintended.megane.runtime.component.block;

import java.util.List;

import badasintended.megane.api.provider.FluidInfoProvider;
import badasintended.megane.api.registry.TooltipRegistry;
import badasintended.megane.runtime.MeganeWaila;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.RenderableTextComponent;
import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;
import net.minecraft.util.registry.Registry;

import static badasintended.megane.runtime.util.RuntimeUtils.fluidName;
import static badasintended.megane.util.MeganeUtils.config;
import static badasintended.megane.util.MeganeUtils.key;

public class FluidComponent extends BlockComponent {

    public static final CompoundTag TAG = new CompoundTag();

    static {
        TAG.putBoolean(key("translate"), false);
        TAG.putString(key("unit"), "mB");
    }

    public FluidComponent() {
        super(() -> config().fluid);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    protected void append(List<Text> tooltip, IDataAccessor accessor) {
        CompoundTag data = accessor.getServerData();
        if (data.getBoolean(key("hasFluid"))) {
            boolean expand = accessor.getPlayer().isSneaking() && config().fluid.isExpandWhenSneak();

            for (int i = 0; i < data.getInt(key("fluidSlotCount")); i++) {

                double stored = data.getDouble(key("storedFluid" + i));
                if (stored == 0)
                    continue;

                double max = data.getDouble(key("maxFluid" + i));

                Fluid fluid = Registry.FLUID.get(data.getInt(key("fluid" + i)));
                FluidInfoProvider provider = TooltipRegistry.FLUID_INFO.get(fluid);

                int color = provider == null
                    ? config().fluid.getBarColor()
                    : provider.getColor(fluid, accessor.getWorld().getBiome(accessor.getPosition()));
                String name = provider == null ? fluidName(fluid) : provider.getName(fluid).getString();

                TAG.putInt(key("color"), color);
                TAG.putDouble(key("stored"), stored);
                TAG.putDouble(key("max"), max);
                TAG.putBoolean(key("verbose"), expand);
                TAG.putString(key("prefix"), name);

                tooltip.add(new RenderableTextComponent(MeganeWaila.BAR, TAG));
            }
        }
    }

}
