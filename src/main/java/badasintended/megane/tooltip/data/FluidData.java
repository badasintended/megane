package badasintended.megane.tooltip.data;

import badasintended.megane.api.registry.FluidTooltipRegistry;
import mcp.mobius.waila.api.IServerDataProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import static badasintended.megane.MeganeUtils.CONFIG;
import static badasintended.megane.MeganeUtils.key;
import static badasintended.megane.api.registry.FluidTooltipRegistry.get;

public class FluidData implements IServerDataProvider<BlockEntity> {

    public static final FluidData INSTANCE = new FluidData();

    void appendInternal(CompoundTag data, World world, BlockEntity blockEntity) {
        FluidTooltipRegistry.Provider provider = get(blockEntity);
        if (provider != null) {
            data.putBoolean(key("hasFluid"), true);

            int slotCount = provider.getSlotCount(blockEntity);
            data.putInt(key("fluidSlotCount"), slotCount);

            for (int i = 0; i < slotCount; i++) {
                data.putString(key("fluidName" + i), provider.getFluidName(blockEntity, i));
                data.putDouble(key("storedFluid" + i), provider.getStored(blockEntity, i));
                data.putDouble(key("maxFluid" + i), provider.getMax(blockEntity, i));
            }
        }
    }

    @Override
    public final void appendServerData(CompoundTag data, ServerPlayerEntity player, World world, BlockEntity blockEntity) {
        if (!CONFIG.get().fluid.isEnabled() || data.getBoolean("hasFluid")) return;
        appendInternal(data, world, blockEntity);
    }

}
