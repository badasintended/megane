package badasintended.megane.runtime.component.block;

import java.util.List;
import java.util.Map;

import badasintended.megane.api.provider.EnergyInfoProvider;
import badasintended.megane.config.MeganeConfig;
import badasintended.megane.runtime.Megane;
import badasintended.megane.runtime.registry.Registrar;
import mcp.mobius.waila.api.IBlockAccessor;
import mcp.mobius.waila.api.ITooltip;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.registry.Registry;

import static badasintended.megane.runtime.util.Keys.B_COLOR;
import static badasintended.megane.runtime.util.Keys.B_LONG;
import static badasintended.megane.runtime.util.Keys.B_MAX;
import static badasintended.megane.runtime.util.Keys.B_PREFIX;
import static badasintended.megane.runtime.util.Keys.B_STORED;
import static badasintended.megane.runtime.util.Keys.B_UNIT;
import static badasintended.megane.runtime.util.Keys.E_HAS;
import static badasintended.megane.runtime.util.Keys.E_MAX;
import static badasintended.megane.runtime.util.Keys.E_STORED;
import static badasintended.megane.util.MeganeUtils.CONFIG;
import static badasintended.megane.util.MeganeUtils.MODID;
import static badasintended.megane.util.MeganeUtils.config;

public class EnergyComponent extends BlockComponent {

    public static final NbtCompound TAG = new NbtCompound();

    public EnergyComponent() {
        super(() -> config().energy);
    }

    @Override
    protected void append(ITooltip tooltip, IBlockAccessor accessor) {
        MeganeConfig.Energy energy = config().energy;
        Map<String, Integer> colors = energy.getColors();
        Map<String, String> units = energy.getUnits();
        NbtCompound data = accessor.getServerData();

        if (data.getBoolean(E_HAS)) {
            double stored = data.getDouble(E_STORED);
            double max = data.getDouble(E_MAX);

            String namespace = Registry.BLOCK.getId(accessor.getBlock()).getNamespace();
            boolean expand = accessor.getPlayer().isSneaking() && energy.isExpandWhenSneak();
            List<EnergyInfoProvider> providers = Registrar.ENERGY_INFO.get(namespace);
            EnergyInfoProvider provider = providers.isEmpty() ? null : providers.get(0);

            int color;
            if (colors.containsKey(namespace)) {
                color = colors.get(namespace);
            } else {
                color = provider == null
                    ? colors.computeIfAbsent(MODID, c -> 0x710C00)
                    : provider.getColor();
                colors.put(namespace, color);
                CONFIG.save();
            }

            String unit;
            if (units.containsKey(namespace)) {
                unit = units.get(namespace);
            } else {
                unit = provider == null
                    ? units.computeIfAbsent(MODID, a -> "E")
                    : provider.getUnit();
                units.put(namespace, unit);
                CONFIG.save();
            }

            TAG.putString(B_PREFIX, provider != null ? provider.getName().getString() : I18n.translate("megane.energy"));
            TAG.putInt(B_COLOR, color);
            TAG.putDouble(B_STORED, stored);
            TAG.putDouble(B_MAX, max);
            TAG.putBoolean(B_LONG, expand);
            TAG.putString(B_UNIT, unit);

            tooltip.addDrawable(Megane.BAR, TAG);
        }
    }

}
