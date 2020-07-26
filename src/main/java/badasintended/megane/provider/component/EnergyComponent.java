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
import static badasintended.megane.MeganeUtils.suffix;

public class EnergyComponent implements IComponentProvider {

    public static final EnergyComponent INSTANCE = new EnergyComponent();

    private static final CompoundTag TAG = new CompoundTag();

    static {
        TAG.putInt(key("color"), 0xFF710C00);
        TAG.putString(key("prefix"), "waila.megane.energy");
        TAG.putBoolean(key("translate"), true);
    }

    private EnergyComponent() {
    }

    @Override
    public void appendBody(List<Text> tooltip, IDataAccessor accessor, IPluginConfig config) {
        if (config.get(Megane.ENERGY)) {
            CompoundTag data = accessor.getServerData();

            if (data.getBoolean(key("hasEnergy"))) {
                double stored = data.getDouble(key("storedEnergy"));
                double max = data.getDouble(key("maxEnergy"));

                boolean sneaking = accessor.getPlayer().isSneaking();
                String storedString;
                if (stored < 0 || stored == Double.MAX_VALUE) {
                    storedString = "∞";
                    stored = 0;
                } else {
                    storedString = sneaking ? String.valueOf(stored) : suffix((long) stored);
                }

                String maxString;
                if (max < 0 || max == Double.MAX_VALUE) {
                    maxString = "∞";
                    max = 0;
                } else {
                    maxString = sneaking ? String.valueOf(max) : suffix((long) max);
                }

                TAG.putDouble(key("stored"), stored);
                TAG.putDouble(key("max"), max);
                TAG.putString(key("text"), String.format("%s/%s %s", storedString, maxString, data.getString(key("energyUnit"))));
                tooltip.add(new RenderableTextComponent(Megane.BAR, TAG));
            }
        }
    }

}
