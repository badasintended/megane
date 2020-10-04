package badasintended.megane.runtime.data;

import badasintended.megane.api.registry.EnergyTooltipRegistry;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import team.reborn.energy.Energy;
import team.reborn.energy.EnergyHandler;

import static badasintended.megane.api.registry.EnergyTooltipRegistry.get;
import static badasintended.megane.util.MeganeUtils.*;

public class EnergyData extends BaseData {

    public static final EnergyData INSTANCE = new EnergyData();

    private EnergyData() {
        super(() -> config().energy);
        appenders.add(new Registered());
        if (hasMod("team_reborn_energy")) {
            LOGGER.info("[megane] Found Team Reborn's Energy, loading compatibility");
            appenders.add(new TeamReborn());
        }
    }

    public static class Registered implements Appender {

        @Override
        @SuppressWarnings({"rawtypes", "unchecked"})
        public boolean append(CompoundTag data, ServerPlayerEntity player, World world, BlockEntity blockEntity) {
            EnergyTooltipRegistry.Provider provider = get(blockEntity);
            if (provider == null || !provider.hasEnergy(blockEntity)) return false;
            data.putBoolean(key("hasEnergy"), true);
            data.putDouble(key("storedEnergy"), provider.getStored(blockEntity));
            data.putDouble(key("maxEnergy"), provider.getMax(blockEntity));
            return true;
        }

    }

    public static class TeamReborn implements Appender {

        @Override
        public boolean append(CompoundTag data, ServerPlayerEntity player, World world, BlockEntity blockEntity) {
            if (Energy.valid(blockEntity)) {
                EnergyHandler energy = Energy.of(blockEntity);
                data.putBoolean(key("hasEnergy"), true);
                data.putDouble(key("storedEnergy"), energy.getEnergy());
                data.putDouble(key("maxEnergy"), energy.getMaxStored());
                return true;
            }
            return false;
        }

    }

}
