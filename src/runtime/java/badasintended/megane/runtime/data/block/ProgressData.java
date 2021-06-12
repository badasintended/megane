package badasintended.megane.runtime.data.block;

import java.util.List;

import badasintended.megane.api.provider.ProgressProvider;
import badasintended.megane.runtime.registry.Registrar;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import static badasintended.megane.runtime.util.Keys.P_HAS;
import static badasintended.megane.runtime.util.Keys.P_I_COUNT;
import static badasintended.megane.runtime.util.Keys.P_I_ID;
import static badasintended.megane.runtime.util.Keys.P_I_NBT;
import static badasintended.megane.runtime.util.Keys.P_I_SIZE;
import static badasintended.megane.runtime.util.Keys.P_O_COUNT;
import static badasintended.megane.runtime.util.Keys.P_O_ID;
import static badasintended.megane.runtime.util.Keys.P_O_NBT;
import static badasintended.megane.runtime.util.Keys.P_O_SIZE;
import static badasintended.megane.runtime.util.Keys.P_PERCENT;
import static badasintended.megane.runtime.util.RuntimeUtils.EMPTY_TAG;
import static badasintended.megane.util.MeganeUtils.config;

public class ProgressData extends BlockData {

    public ProgressData() {
        super(Registrar.PROGRESS, () -> config().progress);
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    void append(NbtCompound data, ServerPlayerEntity player, World world, BlockEntity blockEntity) {
        List<ProgressProvider> providers = Registrar.PROGRESS.get(blockEntity);
        for (ProgressProvider provider : providers) {
            provider.setupContext(world, player);
            if (provider.hasProgress(blockEntity)) {
                data.putBoolean(P_HAS, true);

                int[] inputs = provider.getInputSlots(blockEntity);
                int[] outputs = provider.getOutputSlots(blockEntity);

                int i = 0;
                for (int input : inputs) {
                    ItemStack stack = provider.getStack(blockEntity, input);
                    if (stack.isEmpty()) {
                        continue;
                    }
                    NbtCompound tag = stack.getTag();
                    data.putInt(P_I_ID + i, Registry.ITEM.getRawId(stack.getItem()));
                    data.putInt(P_I_COUNT + i, stack.getCount());
                    data.put(P_I_NBT + i, tag == null ? EMPTY_TAG : tag);
                    i++;
                }
                data.putInt(P_I_SIZE, i);

                data.putInt(P_PERCENT, provider.getPercentage(blockEntity));

                i = 0;
                for (int output : outputs) {
                    ItemStack stack = provider.getStack(blockEntity, output);
                    if (stack.isEmpty()) {
                        continue;
                    }
                    NbtCompound tag = stack.getTag();
                    data.putInt(P_O_ID + i, Registry.ITEM.getRawId(stack.getItem()));
                    data.putInt(P_O_COUNT + i, stack.getCount());
                    data.put(P_O_NBT + i, tag == null ? EMPTY_TAG : tag);
                    i++;
                }
                data.putInt(P_O_SIZE, outputs.length);
                return;
            }
        }
    }

}
