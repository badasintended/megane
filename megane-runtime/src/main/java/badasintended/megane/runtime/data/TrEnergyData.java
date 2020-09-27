package badasintended.megane.runtime.data;

import badasintended.megane.util.MeganeUtils;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import team.reborn.energy.Energy;
import team.reborn.energy.EnergyHandler;

public class TrEnergyData extends EnergyData {

    public static final TrEnergyData INSTANCE = new TrEnergyData();

    @Override
    void appendInternal(CompoundTag data, BlockEntity blockEntity) {
        if (Energy.valid(blockEntity)) {
            EnergyHandler energy = Energy.of(blockEntity);
            data.putBoolean(MeganeUtils.key("hasEnergy"), true);
            data.putDouble(MeganeUtils.key("storedEnergy"), energy.getEnergy());
            data.putDouble(MeganeUtils.key("maxEnergy"), energy.getMaxStored());
        }
    }

}
