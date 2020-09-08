package badasintended.megane.tooltip.component;

import badasintended.megane.Megane;
import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.RenderableTextComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;
import net.minecraft.util.registry.Registry;

import java.util.List;

import static badasintended.megane.MeganeUtils.CONFIG;
import static badasintended.megane.MeganeUtils.key;

public class EnergyComponent implements IComponentProvider {

    public static final EnergyComponent INSTANCE = new EnergyComponent();

    private static final CompoundTag TAG = new CompoundTag();

    static {
        TAG.putString(key("prefix"), "waila.megane.energy");
        TAG.putBoolean(key("translate"), true);
    }

    @Override
    public void appendBody(List<Text> tooltip, IDataAccessor accessor, IPluginConfig config) {
        if (!CONFIG.get().energy.isEnabled()) return;

        CompoundTag data = accessor.getServerData();

        if (data.getBoolean(key("hasEnergy"))) {
            double stored = data.getDouble(key("storedEnergy"));
            double max = data.getDouble(key("maxEnergy"));

            String namespace = Registry.BLOCK.getId(accessor.getBlock()).getNamespace();

            boolean sneaking = accessor.getPlayer().isSneaking();

            TAG.putInt(key("color"), CONFIG.get().energy.getColors().getOrDefault(namespace, 0xFF710C00));
            TAG.putDouble(key("stored"), stored);
            TAG.putDouble(key("max"), max);
            TAG.putBoolean(key("verbose"), sneaking);
            TAG.putString(key("unit"), CONFIG.get().energy.getUnits().getOrDefault(namespace, "EU"));
            tooltip.add(new RenderableTextComponent(Megane.BAR, TAG));
        }
    }

}
