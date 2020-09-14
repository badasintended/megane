package badasintended.megane.tooltip.data;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import team.reborn.energy.Energy;
import team.reborn.energy.EnergyHandler;

import static badasintended.megane.util.MeganeUtils.key;

public class TrEnergyData extends EnergyData {

    public static final TrEnergyData INSTANCE = new TrEnergyData();

    @Override
    void appendInternal(CompoundTag data, BlockEntity blockEntity) {
        if (Energy.valid(blockEntity)) {
            EnergyHandler energy = Energy.of(blockEntity);
            data.putBoolean(key("hasEnergy"), true);
            data.putDouble(key("storedEnergy"), energy.getEnergy());
            data.putDouble(key("maxEnergy"), energy.getMaxStored());
        }
    }

}
