package badasintended.megane.tooltip.data;

import badasintended.megane.api.registry.EnergyTooltipRegistry;
import mcp.mobius.waila.api.IServerDataProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import static badasintended.megane.MeganeUtils.CONFIG;
import static badasintended.megane.MeganeUtils.key;
import static badasintended.megane.api.registry.EnergyTooltipRegistry.get;

public class EnergyData implements IServerDataProvider<BlockEntity> {

    public static final EnergyData INSTANCE = new EnergyData();

    void appendInternal(CompoundTag data, BlockEntity blockEntity) {
        EnergyTooltipRegistry.Provider provider = get(blockEntity);
        if (provider != null) {
            data.putBoolean(key("hasEnergy"), true);
            data.putDouble(key("storedEnergy"), provider.getStored(blockEntity));
            data.putDouble(key("maxEnergy"), provider.getMax(blockEntity));
        }
    }

    @Override
    public final void appendServerData(CompoundTag data, ServerPlayerEntity player, World world, BlockEntity blockEntity) {
        if (!CONFIG.get().energy.isEnabled() || data.getBoolean("hasEnergy")) return;
        appendInternal(data, blockEntity);
    }

}
