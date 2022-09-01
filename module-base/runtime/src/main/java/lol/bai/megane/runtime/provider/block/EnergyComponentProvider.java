package lol.bai.megane.runtime.provider.block;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import lol.bai.megane.api.provider.EnergyInfoProvider;
import lol.bai.megane.api.util.BarFormat;
import lol.bai.megane.runtime.component.StorageAmountComponent;
import lol.bai.megane.runtime.config.MeganeConfig;
import lol.bai.megane.runtime.registry.Registrar;
import mcp.mobius.waila.api.IBlockAccessor;
import mcp.mobius.waila.api.ITooltip;
import mcp.mobius.waila.api.component.PairComponent;
import mcp.mobius.waila.api.component.WrappedComponent;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.registry.Registry;

import static lol.bai.megane.runtime.util.Keys.E_HAS;
import static lol.bai.megane.runtime.util.Keys.E_MAX;
import static lol.bai.megane.runtime.util.Keys.E_STORED;
import static lol.bai.megane.runtime.util.MeganeUtils.CONFIG;
import static lol.bai.megane.runtime.util.MeganeUtils.MODID;
import static lol.bai.megane.runtime.util.MeganeUtils.config;

public class EnergyComponentProvider extends BlockComponentProvider {

    private static final TranslatableText ENERGY_NAME = new TranslatableText("megane.energy");

    public EnergyComponentProvider() {
        super(() -> config().energy);
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    protected void append(ITooltip tooltip, IBlockAccessor accessor) {
        MeganeConfig.Energy energy = config().energy;
        Map<String, Integer> colors = energy.getColors();
        Map<String, String> units = energy.getUnits();
        NbtCompound data = accessor.getServerData();

        if (data.getBoolean(E_HAS) && accessor.getBlockEntity() != null) {
            double stored = data.getDouble(E_STORED);
            double max = data.getDouble(E_MAX);

            String key;
            List<EnergyInfoProvider> providers = Registrar.ENERGY_INFO.get(accessor.getBlockEntity());
            if (providers.isEmpty()) {
                key = Registry.BLOCK.getId(accessor.getBlock()).getNamespace();
                providers = Registrar.ENERGY_INFO.get(key);
            } else {
                key = Registry.BLOCK_ENTITY_TYPE.getId(accessor.getBlockEntity().getType()).toString();
            }

            boolean expand = accessor.getPlayer().isSneaking() && energy.isExpandWhenSneak();
            EnergyInfoProvider provider = null;

            for (EnergyInfoProvider p : providers) {
                p.setContext(accessor.getWorld(), accessor.getPosition(), accessor.getHitResult(), accessor.getPlayer(), accessor.getBlockEntity());
                if (p.hasEnergyInfo()) {
                    provider = p;
                    break;
                }
            }

            int color;
            if (colors.containsKey(key)) {
                color = colors.get(key);
            } else {
                color = provider == null
                    ? colors.computeIfAbsent(MODID, c -> 0x710C00)
                    : provider.getColor() & 0xFFFFFF;
                colors.put(key, color);
                CONFIG.save();
            }

            String unit;
            if (units.containsKey(key)) {
                unit = units.get(key);
            } else {
                unit = provider == null
                    ? units.computeIfAbsent(MODID, a -> "E")
                    : Objects.requireNonNullElse(provider.getUnit(), "E");
                units.put(key, unit);
                CONFIG.save();
            }

            tooltip.addLine(new PairComponent(
                new WrappedComponent(provider != null ? provider.getName() : ENERGY_NAME),
                new StorageAmountComponent(provider != null ? provider.getFormat() : BarFormat.FRACTION, color, stored, max, unit, expand)));
        }
    }

}
