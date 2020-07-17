package badasintended.megane.provider.data;

import mcp.mobius.waila.api.IServerDataProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import static badasintended.megane.Utils.key;
import static badasintended.megane.api.EnergyTooltipRegistry.*;

public class RegisteredEnergyData implements IServerDataProvider<BlockEntity> {

    public static final RegisteredEnergyData INSTANCE = new RegisteredEnergyData();

    public RegisteredEnergyData() {
    }

    @Override
    public void appendServerData(CompoundTag data, ServerPlayerEntity player, World world, BlockEntity blockEntity) {
        if (valid(blockEntity)) {
            data.putBoolean(key("hasEnergy"), true);
            data.putString(key("energyUnit"), getUnit(blockEntity));
            data.putDouble(key("storedEnergy"), getStored(blockEntity));
            data.putDouble(key("maxEnergy"), getMax(blockEntity));
        }
    }

}
