package badasintended.megane.runtime.data.entity;

import java.util.function.Supplier;

import badasintended.megane.config.MeganeConfig;
import badasintended.megane.runtime.registry.Registry;
import mcp.mobius.waila.api.IServerDataProvider;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import static badasintended.megane.util.MeganeUtils.ISSUE_URL;
import static badasintended.megane.util.MeganeUtils.LOGGER;
import static badasintended.megane.util.MeganeUtils.config;
import static net.minecraft.util.registry.Registry.ENTITY_TYPE;

public abstract class EntityData implements IServerDataProvider<Entity> {

    private static final Text ERROR_TEXT = new LiteralText("Something went wrong when retrieving data for this entity").styled(style -> style
        .withColor(Formatting.RED)
        .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new LiteralText("Click me to open an issue at GitHub")))
        .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, ISSUE_URL)));

    private final Registry<?> registry;
    private final Supplier<MeganeConfig.Base> baseConfig;

    public EntityData(Supplier<MeganeConfig.Base> baseConfig) {
        this(null, baseConfig);
    }

    public EntityData(Registry<?> registry, Supplier<MeganeConfig.Base> baseConfig) {
        this.registry = registry;
        this.baseConfig = baseConfig;
    }

    abstract void append(NbtCompound data, ServerPlayerEntity player, World world, Entity entity);

    @Override
    public final void appendServerData(NbtCompound data, ServerPlayerEntity player, World world, Entity entity) {
        if (!baseConfig.get().isEnabled() || baseConfig.get().getBlacklist().contains(ENTITY_TYPE.getId(entity.getType()))) {
            return;
        }

        try {
            append(data, player, world, entity);
        } catch (Throwable t) {
            Vec3d pos = entity.getPos();
            player.sendSystemMessage(ERROR_TEXT, Util.NIL_UUID);
            LOGGER.error("Something went wrong when retrieving data for {} at ({}, {}, {})", entity.getClass().getName(), pos.getX(), pos.getY(), pos.getZ());
            if (!config().getCatchServerErrors()) {
                throw t;
            }
            LOGGER.error("Stacktrace:", t);

            if (registry != null) {
                registry.error(entity);
            }
        }
    }

}
