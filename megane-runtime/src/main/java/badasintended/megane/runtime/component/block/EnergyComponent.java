package badasintended.megane.runtime.component.block;

import java.util.List;

import badasintended.megane.config.MeganeConfig;
import badasintended.megane.runtime.MeganeWaila;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.RenderableTextComponent;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;
import net.minecraft.util.registry.Registry;

import static badasintended.megane.util.MeganeUtils.MODID;
import static badasintended.megane.util.MeganeUtils.config;
import static badasintended.megane.util.MeganeUtils.key;

public class EnergyComponent extends BlockComponent {

    public static final CompoundTag TAG = new CompoundTag();

    public EnergyComponent() {
        super(() -> config().energy);
    }

    @Override
    protected void append(List<Text> tooltip, IDataAccessor accessor) {
        MeganeConfig.Energy energy = config().energy;
        CompoundTag data = accessor.getServerData();

        if (data.getBoolean(key("hasEnergy"))) {
            double stored = data.getDouble(key("storedEnergy"));
            double max = data.getDouble(key("maxEnergy"));

            String namespace = Registry.BLOCK.getId(accessor.getBlock()).getNamespace();

            boolean expand = accessor.getPlayer().isSneaking() && energy.isExpandWhenSneak();

            TAG.putString(key("prefix"), I18n.translate("megane.energy"));
            TAG.putInt(key("color"), energy.getColors().getOrDefault(namespace, energy.getColors().getOrDefault(MODID, 0xFF710C00)));
            TAG.putDouble(key("stored"), stored);
            TAG.putDouble(key("max"), max);
            TAG.putBoolean(key("verbose"), expand);
            TAG.putString(key("unit"), energy.getUnits().getOrDefault(namespace, energy.getUnits().getOrDefault(MODID, "E")));
            tooltip.add(new RenderableTextComponent(MeganeWaila.BAR, TAG));
        }
    }

}
