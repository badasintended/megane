package badasintended.megane.runtime.data.block;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import badasintended.megane.config.MeganeConfig;
import badasintended.megane.runtime.data.Appender;
import mcp.mobius.waila.api.IServerDataProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public abstract class BlockData implements IServerDataProvider<BlockEntity> {

    protected final List<Appender<BlockEntity>> appenders = new ArrayList<>();

    private final Supplier<MeganeConfig.Base> baseConfig;

    public BlockData(Supplier<MeganeConfig.Base> baseConfig) {
        this.baseConfig = baseConfig;
    }

    @Override
    public final void appendServerData(CompoundTag data, ServerPlayerEntity player, World world, BlockEntity blockEntity) {
        if (!baseConfig.get().isEnabled() || baseConfig.get().getBlacklist().contains(Registry.BLOCK.getId(blockEntity.getCachedState().getBlock()))) {
            return;
        }

        for (Appender<BlockEntity> appender : appenders) {
            if (appender.append(data, player, world, blockEntity))
                break;
        }
    }

}
