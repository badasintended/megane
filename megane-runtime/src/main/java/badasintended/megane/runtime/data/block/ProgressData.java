package badasintended.megane.runtime.data.block;

import badasintended.megane.api.provider.ProgressProvider;
import badasintended.megane.api.registry.TooltipRegistry;
import badasintended.megane.runtime.data.Appender;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import static badasintended.megane.util.MeganeUtils.config;
import static badasintended.megane.util.MeganeUtils.key;

public class ProgressData extends BlockData {

    public ProgressData() {
        super(() -> config().progress);
        appenders.add(new Registered());
    }

    public static class Registered implements Appender<BlockEntity> {

        @Override
        @SuppressWarnings({"rawtypes", "unchecked"})
        public boolean append(CompoundTag data, ServerPlayerEntity player, World world, BlockEntity blockEntity) {
            ProgressProvider provider = TooltipRegistry.PROGRESS.get(blockEntity);
            if (provider != null && provider.hasProgress(blockEntity)) {
                data.putBoolean(key("hasProgress"), true);

                int[] inputs = provider.getInputSlots(blockEntity);
                int[] outputs = provider.getOutputSlots(blockEntity);
                data.putInt(key("inputCount"), inputs.length);
                data.putInt(key("outputCount"), outputs.length);

                for (int i = 0; i < inputs.length; i++) {
                    data.put(key("input" + i), provider.getStack(blockEntity, inputs[i]).toTag(new CompoundTag()));
                }

                data.putInt(key("percentage"), provider.getPercentage(blockEntity));

                for (int i = 0; i < outputs.length; i++) {
                    data.put(key("output" + i), provider.getStack(blockEntity, outputs[i]).toTag(new CompoundTag()));
                }

                return true;
            }
            return false;
        }

    }

}
