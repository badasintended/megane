package badasintended.megane.provider.data;

import mcp.mobius.waila.api.IServerDataProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import static badasintended.megane.MeganeUtils.key;
import static badasintended.megane.api.registry.EnergyTooltipRegistry.*;

public class RegisteredEnergyData implements IServerDataProvider<BlockEntity> {

    public static final RegisteredEnergyData INSTANCE = new RegisteredEnergyData();

    private RegisteredEnergyData() {
    }

    @Override
    public void appendServerData(CompoundTag data, ServerPlayerEntity player, World world, BlockEntity blockEntity) {
        Class<? extends BlockEntity> registeredClass = getRegisteredClass(blockEntity);
        if (registeredClass != null) {
            data.putBoolean(key("hasEnergy"), true);
            data.putString(key("energyUnit"), getUnit(registeredClass));
            data.putDouble(key("storedEnergy"), getStored(registeredClass, blockEntity));
            data.putDouble(key("maxEnergy"), getMax(registeredClass, blockEntity));
        }
    }

}
