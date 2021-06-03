package badasintended.megane.runtime.data.entity;

import java.util.function.Supplier;

import badasintended.megane.config.MeganeConfig;
import badasintended.megane.runtime.registry.Registry;
import mcp.mobius.waila.api.IServerDataProvider;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import static badasintended.megane.util.MeganeUtils.LOGGER;
import static net.minecraft.util.registry.Registry.ENTITY_TYPE;

public abstract class EntityData implements IServerDataProvider<LivingEntity> {

    private final Registry<?> registry;
    private final Supplier<MeganeConfig.Base> baseConfig;

    public EntityData(Supplier<MeganeConfig.Base> baseConfig) {
        this(null, baseConfig);
    }

    public EntityData(Registry<?> registry, Supplier<MeganeConfig.Base> baseConfig) {
        this.registry = registry;
        this.baseConfig = baseConfig;
    }

    abstract void append(CompoundTag data, ServerPlayerEntity player, World world, LivingEntity entity);

    @Override
    public final void appendServerData(CompoundTag data, ServerPlayerEntity player, World world, LivingEntity entity) {
        if (!baseConfig.get().isEnabled() || baseConfig.get().getBlacklist().contains(ENTITY_TYPE.getId(entity.getType()))) {
            return;
        }

        try {
            append(data, player, world, entity);
        } catch (Exception e) {
            Vec3d pos = entity.getPos();
            LOGGER.error("Something went wrong when retrieving data for {} at ({}, {}, {})", entity.getClass().getName(), pos.getX(), pos.getY(), pos.getZ());
            LOGGER.error("Stacktrace:", e);

            if (registry != null) {
                registry.error(entity);
            }
        }
    }

}
