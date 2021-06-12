package badasintended.megane.runtime.data;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

public interface Appender<T> {

    boolean append(NbtCompound data, ServerPlayerEntity player, World world, T t);

}
