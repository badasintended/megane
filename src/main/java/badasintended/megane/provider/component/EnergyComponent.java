package badasintended.megane.provider.component;

import badasintended.megane.PluginMegane;
import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;

import java.util.List;

import static badasintended.megane.Utils.*;

public class EnergyComponent implements IComponentProvider {

    public static final EnergyComponent INSTANCE = new EnergyComponent();

    private EnergyComponent() {
    }

    @Override
    public void appendBody(List<Text> tooltip, IDataAccessor accessor, IPluginConfig config) {
        if (config.get(PluginMegane.Config.ENERGY)) {
            CompoundTag data = accessor.getServerData();

            if (data.getBoolean(key("hasEnergy"))) {
                double stored = data.getDouble(key("storedEnergy"));
                double max = data.getDouble(key("maxEnergy"));

                boolean sneaking = accessor.getPlayer().isSneaking();
                String storedString = sneaking ? String.valueOf(stored) : suffix((long) stored);
                String maxString = sneaking ? String.valueOf(max) : suffix((long) max);

                tooltip.add(tlText("energy", storedString, maxString, data.getString(key("energyUnit"))));
            }
        }
    }

}
