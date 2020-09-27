package badasintended.megane.runtime.data;

import badasintended.megane.api.registry.EnergyTooltipRegistry;
import badasintended.megane.util.MeganeUtils;
import mcp.mobius.waila.api.IServerDataProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import static badasintended.megane.api.registry.EnergyTooltipRegistry.get;

public class EnergyData implements IServerDataProvider<BlockEntity> {

    public static final EnergyData INSTANCE = new EnergyData();

    @SuppressWarnings({"rawtypes", "unchecked"})
    void appendInternal(CompoundTag data, BlockEntity blockEntity) {
        EnergyTooltipRegistry.Provider provider = get(blockEntity);
        if (provider != null) {
            data.putBoolean(MeganeUtils.key("hasEnergy"), true);
            data.putDouble(MeganeUtils.key("storedEnergy"), provider.getStored(blockEntity));
            data.putDouble(MeganeUtils.key("maxEnergy"), provider.getMax(blockEntity));
        }
    }

    @Override
    public final void appendServerData(CompoundTag data, ServerPlayerEntity player, World world, BlockEntity blockEntity) {
        if (!MeganeUtils.config().energy.isEnabled() || MeganeUtils.config().energy.getBlacklist().contains(Registry.BLOCK.getId(blockEntity.getCachedState().getBlock()))) {
            return;
        }
        appendInternal(data, blockEntity);
    }

}
