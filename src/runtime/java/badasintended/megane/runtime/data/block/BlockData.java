package badasintended.megane.runtime.data.block;

import java.util.function.Supplier;

import badasintended.megane.config.MeganeConfig;
import badasintended.megane.runtime.registry.Registry;
import mcp.mobius.waila.api.IServerDataProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static badasintended.megane.util.MeganeUtils.LOGGER;
import static net.minecraft.util.registry.Registry.BLOCK;

public abstract class BlockData implements IServerDataProvider<BlockEntity> {

    private final Registry<?> registry;
    private final Supplier<MeganeConfig.Base> baseConfig;

    public BlockData(Supplier<MeganeConfig.Base> baseConfig) {
        this(null, baseConfig);
    }

    public BlockData(Registry<?> registry, Supplier<MeganeConfig.Base> baseConfig) {
        this.registry = registry;
        this.baseConfig = baseConfig;
    }

    abstract void append(CompoundTag data, ServerPlayerEntity player, World world, BlockEntity blockEntity);

    @Override
    public final void appendServerData(CompoundTag data, ServerPlayerEntity player, World world, BlockEntity blockEntity) {
        if (!baseConfig.get().isEnabled() || baseConfig.get().getBlacklist().contains(BLOCK.getId(blockEntity.getCachedState().getBlock()))) {
            return;
        }

        try {
            append(data, player, world, blockEntity);
        } catch (Exception e) {
            BlockPos pos = blockEntity.getPos();
            LOGGER.error("Something went wrong when retrieving data for {} at ({}, {}, {})", blockEntity.getClass().getName(), pos.getX(), pos.getY(), pos.getZ());
            LOGGER.error("Stacktrace:", e);

            if (registry != null) {
                registry.error(blockEntity);
            }
        }
    }

}
