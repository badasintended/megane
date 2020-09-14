package badasintended.megane.tooltip.component;

import badasintended.megane.Megane;
import badasintended.megane.config.MeganeConfig;
import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.RenderableTextComponent;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;
import net.minecraft.util.registry.Registry;

import java.util.List;

import static badasintended.megane.util.MeganeUtils.*;

public class EnergyComponent implements IComponentProvider {

    public static final EnergyComponent INSTANCE = new EnergyComponent();

    private static final CompoundTag TAG = new CompoundTag();

    @Override
    public void appendBody(List<Text> tooltip, IDataAccessor accessor, IPluginConfig config) {
        MeganeConfig.Energy energy = config().energy;
        if (!energy.isEnabled()) return;

        CompoundTag data = accessor.getServerData();

        if (data.getBoolean(key("hasEnergy"))) {
            double stored = data.getDouble(key("storedEnergy"));
            double max = data.getDouble(key("maxEnergy"));

            String namespace = Registry.BLOCK.getId(accessor.getBlock()).getNamespace();

            boolean expand = accessor.getPlayer().isSneaking() && energy.isExpandWhenSneak();

            TAG.putString(key("prefix"), I18n.translate("waila.megane.energy"));
            TAG.putInt(key("color"), energy.getColors().getOrDefault(namespace, energy.getColors().getOrDefault(MODID, 0xFF710C00)));
            TAG.putDouble(key("stored"), stored);
            TAG.putDouble(key("max"), max);
            TAG.putBoolean(key("verbose"), expand);
            TAG.putString(key("unit"), energy.getUnits().getOrDefault(namespace, energy.getUnits().getOrDefault(MODID, "E")));
            tooltip.add(new RenderableTextComponent(Megane.BAR, TAG));
        }
    }

}
