package badasintended.megane.runtime.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

public interface Appender<T> {

    boolean append(CompoundTag data, ServerPlayerEntity player, World world, T t);

}
