package badasintended.megane.provider.data;

import mcp.mobius.waila.api.IServerDataProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import static badasintended.megane.MeganeUtils.key;
import static badasintended.megane.api.registry.FluidTooltipRegistry.*;

public class FluidData implements IServerDataProvider<BlockEntity> {

    public static final FluidData INSTANCE = new FluidData();

    private FluidData() {
    }

    @Override
    public void appendServerData(CompoundTag data, ServerPlayerEntity player, World world, BlockEntity blockEntity) {
        Class<? extends BlockEntity> registeredClass = getRegisteredClass(blockEntity);
        if (registeredClass != null) {
            data.putBoolean(key("hasFluid"), true);

            int slotCount = getSlotCount(registeredClass, blockEntity);
            data.putInt(key("fluidSlotCount"), slotCount);

            for (int i = 0; i < slotCount; i++) {
                data.putString(key("fluidName" + i), getFluidName(registeredClass, blockEntity, i));
                data.putDouble(key("storedFluid" + i), getStored(registeredClass, blockEntity, i));
                data.putDouble(key("maxFluid" + i), getMax(registeredClass, blockEntity, i));
            }
        }
    }

}
