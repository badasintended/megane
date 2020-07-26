package badasintended.megane.provider.data;

import mcp.mobius.waila.api.IServerDataProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import static badasintended.megane.MeganeUtils.key;
import static badasintended.megane.api.registry.ProgressTooltipRegistry.*;

public class ProgressData implements IServerDataProvider<BlockEntity> {

    public static final ProgressData INSTANCE = new ProgressData();

    private ProgressData() {
    }

    @Override
    public void appendServerData(CompoundTag data, ServerPlayerEntity player, World world, BlockEntity blockEntity) {
        Class<? extends BlockEntity> registeredClass = getRegisteredClass(blockEntity);
        if (registeredClass != null) {
            data.putBoolean(key("hasProgress"), true);

            int inputCount = getInputSlotCount(registeredClass, blockEntity);
            int outputCount = getOutputSlotCount(registeredClass, blockEntity);
            data.putInt(key("inputCount"), inputCount);
            data.putInt(key("outputCount"), outputCount);

            for (int i = 0; i < inputCount; i++) {
                data.put(key("input" + i), getInputStack(registeredClass, blockEntity, i).toTag(new CompoundTag()));
            }

            data.putInt(key("percentage"), getPercentage(registeredClass, blockEntity));

            for (int i = 0; i < outputCount; i++) {
                data.put(key("output" + i), getOutputStack(registeredClass, blockEntity, i).toTag(new CompoundTag()));
            }
        }
    }

}
