package badasintended.megane.runtime.data.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import badasintended.megane.config.MeganeConfig;
import badasintended.megane.runtime.data.Appender;
import mcp.mobius.waila.api.IServerDataProvider;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public abstract class EntityData implements IServerDataProvider<LivingEntity> {

    protected final List<Appender<LivingEntity>> appenders = new ArrayList<>();

    private final Supplier<MeganeConfig.Base> baseConfig;

    public EntityData(Supplier<MeganeConfig.Base> baseConfig) {
        this.baseConfig = baseConfig;
    }

    @Override
    public void appendServerData(CompoundTag data, ServerPlayerEntity player, World world, LivingEntity entity) {
        if (!baseConfig.get().isEnabled() || baseConfig.get().getBlacklist().contains(Registry.ENTITY_TYPE.getId(entity.getType()))) {
            return;
        }

        for (Appender<LivingEntity> appender : appenders) {
            if (appender.append(data, player, world, entity)) break;
        }
    }

}
