package badasintended.megane.runtime.component;

import badasintended.megane.runtime.MeganeWaila;
import badasintended.megane.config.MeganeConfig;
import badasintended.megane.util.MeganeUtils;
import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.RenderableTextComponent;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;
import net.minecraft.util.registry.Registry;

import java.util.List;

public class EnergyComponent implements IComponentProvider {

    public static final EnergyComponent INSTANCE = new EnergyComponent();

    private static final CompoundTag TAG = new CompoundTag();

    @Override
    public void appendBody(List<Text> tooltip, IDataAccessor accessor, IPluginConfig config) {
        MeganeConfig.Energy energy = MeganeUtils.config().energy;
        if (!energy.isEnabled()) return;

        CompoundTag data = accessor.getServerData();

        if (data.getBoolean(MeganeUtils.key("hasEnergy"))) {
            double stored = data.getDouble(MeganeUtils.key("storedEnergy"));
            double max = data.getDouble(MeganeUtils.key("maxEnergy"));

            String namespace = Registry.BLOCK.getId(accessor.getBlock()).getNamespace();

            boolean expand = accessor.getPlayer().isSneaking() && energy.isExpandWhenSneak();

            TAG.putString(MeganeUtils.key("prefix"), I18n.translate("waila.megane.energy"));
            TAG.putInt(MeganeUtils.key("color"), energy.getColors().getOrDefault(namespace, energy.getColors().getOrDefault(MeganeUtils.MODID, 0xFF710C00)));
            TAG.putDouble(MeganeUtils.key("stored"), stored);
            TAG.putDouble(MeganeUtils.key("max"), max);
            TAG.putBoolean(MeganeUtils.key("verbose"), expand);
            TAG.putString(MeganeUtils.key("unit"), energy.getUnits().getOrDefault(namespace, energy.getUnits().getOrDefault(MeganeUtils.MODID, "E")));
            tooltip.add(new RenderableTextComponent(MeganeWaila.BAR, TAG));
        }
    }

}
