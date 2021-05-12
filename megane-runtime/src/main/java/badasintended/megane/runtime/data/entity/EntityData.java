package badasintended.megane.runtime.data.entity;

import java.util.function.Supplier;

import badasintended.megane.config.MeganeConfig;
import mcp.mobius.waila.api.IServerDataProvider;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public abstract class EntityData implements IServerDataProvider<LivingEntity> {

    private final Supplier<MeganeConfig.Base> baseConfig;

    public EntityData(Supplier<MeganeConfig.Base> baseConfig) {
        this.baseConfig = baseConfig;
    }

    abstract void append(CompoundTag data, ServerPlayerEntity player, World world, LivingEntity entity);

    @Override
    public final void appendServerData(CompoundTag data, ServerPlayerEntity player, World world, LivingEntity entity) {
        if (!baseConfig.get().isEnabled() || baseConfig.get().getBlacklist().contains(Registry.ENTITY_TYPE.getId(entity.getType()))) {
            return;
        }

        append(data, player, world, entity);
    }

}
