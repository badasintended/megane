package badasintended.megane.provider;

import badasintended.megane.Megane;
import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerDataProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import team.reborn.energy.Energy;
import team.reborn.energy.EnergyHandler;

import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import static badasintended.megane.TextUtil.translate;

public class EnergyProvider implements IComponentProvider, IServerDataProvider<BlockEntity> {

    public static final EnergyProvider INSTANCE = new EnergyProvider();

    public static final NavigableMap<Long, String> suffixes = new TreeMap<>();

    static {
        suffixes.put(1000L, "K");
        suffixes.put(1000000L, "M");
        suffixes.put(1000000000L, "G");
        suffixes.put(1000000000000L, "T");
        suffixes.put(1000000000000000L, "P");
        suffixes.put(1000000000000000000L, "E");
    }

    private static String format(long value) {
        if (value == Long.MIN_VALUE) return format(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + format(-value);
        if (value < 1000) return Long.toString(value);

        Map.Entry<Long, String> e = suffixes.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();
        long truncated = value / (divideBy / 10);
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }

    @Override
    public void appendBody(List<Text> tooltip, IDataAccessor accessor, IPluginConfig config) {
        if (config.get(Megane.Config.ENERGY_TR)) {
            CompoundTag data = accessor.getServerData();

            if (data.getBoolean("tr/hasEnergy")) {
                double stored = data.getDouble("tr/stored");
                double max = data.getDouble("tr/max");

                boolean sneaking = accessor.getPlayer().isSneaking();
                String storedString = sneaking ? String.valueOf(stored) : format((long) stored);
                String maxString = sneaking ? String.valueOf(max) : format((long) max);

                tooltip.add(translate("energy.techreborn.stored", storedString, maxString));
            }
        }
    }

    @Override
    public void appendServerData(CompoundTag data, ServerPlayerEntity player, World world, BlockEntity blockEntity) {
        if (Energy.valid(blockEntity)) {
            EnergyHandler energy = Energy.of(blockEntity);
            data.putBoolean("tr/hasEnergy", true);
            data.putDouble("tr/stored", energy.getEnergy());
            data.putDouble("tr/max", energy.getMaxStored());
        }
    }

}
