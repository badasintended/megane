package badasintended.megane.runtime.data;

import badasintended.megane.api.registry.FluidTooltipRegistry;
import badasintended.megane.util.MeganeUtils;
import mcp.mobius.waila.api.IServerDataProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import static badasintended.megane.api.registry.FluidTooltipRegistry.get;

public class FluidData implements IServerDataProvider<BlockEntity> {

    public static final FluidData INSTANCE = new FluidData();

    @SuppressWarnings({"rawtypes", "unchecked"})
    void appendInternal(CompoundTag data, World world, BlockEntity blockEntity) {
        FluidTooltipRegistry.Provider provider = get(blockEntity);
        if (provider != null) {
            data.putBoolean(MeganeUtils.key("hasFluid"), true);

            int slotCount = provider.getSlotCount(blockEntity);
            data.putInt(MeganeUtils.key("fluidSlotCount"), slotCount);

            for (int i = 0; i < slotCount; i++) {
                data.putString(MeganeUtils.key("fluidName" + i), provider.getFluidName(blockEntity, i).getString());
                data.putDouble(MeganeUtils.key("storedFluid" + i), provider.getStored(blockEntity, i));
                data.putDouble(MeganeUtils.key("maxFluid" + i), provider.getMax(blockEntity, i));
            }
        }
    }

    @Override
    public final void appendServerData(CompoundTag data, ServerPlayerEntity player, World world, BlockEntity blockEntity) {
        if (!MeganeUtils.config().fluid.isEnabled() || MeganeUtils.config().fluid.getBlacklist().contains(Registry.BLOCK.getId(blockEntity.getCachedState().getBlock()))) {
            return;
        }
        appendInternal(data, world, blockEntity);
    }

}
