package badasintended.megane.provider.data;

import mcp.mobius.waila.api.IServerDataProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import team.reborn.energy.Energy;
import team.reborn.energy.EnergyHandler;

import static badasintended.megane.MeganeUtils.key;

public class TeamRebornEnergyData implements IServerDataProvider<BlockEntity> {

    public static final TeamRebornEnergyData INSTANCE = new TeamRebornEnergyData();

    private TeamRebornEnergyData() {
    }

    @Override
    public void appendServerData(CompoundTag data, ServerPlayerEntity player, World world, BlockEntity blockEntity) {
        if (Energy.valid(blockEntity)) {
            EnergyHandler energy = Energy.of(blockEntity);
            data.putBoolean(key("hasEnergy"), true);
            data.putString(key("energyUnit"), "EU");
            data.putDouble(key("storedEnergy"), energy.getEnergy());
            data.putDouble(key("maxEnergy"), energy.getMaxStored());
        }
    }

}
