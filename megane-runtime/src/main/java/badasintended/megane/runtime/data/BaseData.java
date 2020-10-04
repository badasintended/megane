package badasintended.megane.runtime.data;

import badasintended.megane.config.MeganeConfig;
import mcp.mobius.waila.api.IServerDataProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class BaseData implements IServerDataProvider<BlockEntity> {

    protected final List<Appender> appenders = new ArrayList<>();

    private final Supplier<MeganeConfig.Base> baseConfig;

    public BaseData(Supplier<MeganeConfig.Base> baseConfig) {
        this.baseConfig = baseConfig;
    }

    @Override
    public void appendServerData(CompoundTag data, ServerPlayerEntity player, World world, BlockEntity blockEntity) {
        if (!baseConfig.get().isEnabled() || baseConfig.get().getBlacklist().contains(Registry.BLOCK.getId(blockEntity.getCachedState().getBlock()))) {
            return;
        }

        for (Appender appender : appenders) {
            if (appender.append(data, player, world, blockEntity)) break;
        }
    }

}
