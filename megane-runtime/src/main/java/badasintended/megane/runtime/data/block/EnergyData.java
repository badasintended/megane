package badasintended.megane.runtime.data.block;

import badasintended.megane.api.provider.EnergyProvider;
import badasintended.megane.runtime.data.Appender;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import team.reborn.energy.Energy;
import team.reborn.energy.EnergyHandler;

import static badasintended.megane.api.registry.TooltipRegistry.ENERGY;
import static badasintended.megane.runtime.util.Keys.E_HAS;
import static badasintended.megane.runtime.util.Keys.E_MAX;
import static badasintended.megane.runtime.util.Keys.E_STORED;
import static badasintended.megane.runtime.util.RuntimeUtils.errorData;
import static badasintended.megane.util.MeganeUtils.LOGGER;
import static badasintended.megane.util.MeganeUtils.config;
import static badasintended.megane.util.MeganeUtils.hasMod;

public class EnergyData extends BlockData {

    public EnergyData() {
        super(() -> config().energy);
        appenders.add(new Registered());
        if (hasMod("team_reborn_energy")) {
            LOGGER.info("[megane] EnergyData: Found Team Reborn's Energy, loading compatibility");
            appenders.add(new TeamReborn());
        }
    }

    public static class Registered implements Appender<BlockEntity> {

        @Override
        @SuppressWarnings({"rawtypes", "unchecked"})
        public boolean append(CompoundTag data, ServerPlayerEntity player, World world, BlockEntity blockEntity) {
            boolean forceRegistry = config().energy.isForceRegistry();
            try {
                EnergyProvider energyProvider = ENERGY.get(blockEntity);
                if (energyProvider == null || !energyProvider.hasEnergy(blockEntity))
                    return forceRegistry;
                data.putBoolean(E_HAS, true);
                data.putDouble(E_STORED, energyProvider.getStored(blockEntity));
                data.putDouble(E_MAX, energyProvider.getMax(blockEntity));
                return true;
            } catch (Exception e) {
                errorData(ENERGY, blockEntity, e);
                return forceRegistry;
            }
        }

    }

    public static class TeamReborn implements Appender<BlockEntity> {

        @Override
        public boolean append(CompoundTag data, ServerPlayerEntity player, World world, BlockEntity blockEntity) {
            if (Energy.valid(blockEntity)) {
                EnergyHandler energy = Energy.of(blockEntity);
                data.putBoolean(E_HAS, true);
                data.putDouble(E_STORED, energy.getEnergy());
                data.putDouble(E_MAX, energy.getMaxStored());
                return true;
            }
            return false;
        }

    }

}
