package badasintended.megane.runtime.data.block;

import java.util.function.Supplier;

import badasintended.megane.config.MeganeConfig;
import mcp.mobius.waila.api.IServerDataProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public abstract class BlockData implements IServerDataProvider<BlockEntity> {

    private final Supplier<MeganeConfig.Base> baseConfig;

    public BlockData(Supplier<MeganeConfig.Base> baseConfig) {
        this.baseConfig = baseConfig;
    }

    abstract void append(CompoundTag data, ServerPlayerEntity player, World world, BlockEntity blockEntity);

    @Override
    public final void appendServerData(CompoundTag data, ServerPlayerEntity player, World world, BlockEntity blockEntity) {
        if (!baseConfig.get().isEnabled() || baseConfig.get().getBlacklist().contains(Registry.BLOCK.getId(blockEntity.getCachedState().getBlock()))) {
            return;
        }

        append(data, player, world, blockEntity);
    }

}
