package lol.bai.megane.runtime.data.entity;

import java.util.function.Supplier;

import lol.bai.megane.api.provider.AbstractProvider;
import lol.bai.megane.runtime.config.MeganeConfig;
import lol.bai.megane.runtime.registry.Registry;
import lol.bai.megane.runtime.util.MeganeUtils;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.IServerDataProvider;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;

import static net.minecraft.util.registry.Registry.ENTITY_TYPE;

public abstract class EntityData implements IServerDataProvider<Entity> {

    private static final Text ERROR_TEXT = Text.literal("Something went wrong when retrieving data for this entity").styled(style -> style
        .withColor(Formatting.RED)
        .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal("Click me to open an issue at GitHub")))
        .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, MeganeUtils.ISSUE_URL)));

    private final Registry<?> registry;
    private final Supplier<MeganeConfig.Base> baseConfig;

    public EntityData(Supplier<MeganeConfig.Base> baseConfig) {
        this(null, baseConfig);
    }

    public EntityData(Registry<?> registry, Supplier<MeganeConfig.Base> baseConfig) {
        this.registry = registry;
        this.baseConfig = baseConfig;
    }

    abstract void append(NbtCompound data, IServerAccessor<Entity> accessor);

    @Override
    public void appendServerData(NbtCompound data, IServerAccessor<Entity> accessor, IPluginConfig config) {
        Entity entity = accessor.getTarget();
        if (!baseConfig.get().isEnabled() || baseConfig.get().getBlacklist().contains(ENTITY_TYPE.getId(entity.getType()))) {
            return;
        }

        try {
            append(data, accessor);
        } catch (Throwable t) {
            Vec3d pos = entity.getPos();
            accessor.getPlayer().sendMessage(ERROR_TEXT);
            MeganeUtils.LOGGER.error("Something went wrong when retrieving data for {} at ({}, {}, {})", entity.getClass().getName(), pos.getX(), pos.getY(), pos.getZ());
            if (!MeganeUtils.config().getCatchServerErrors()) {
                throw t;
            }
            MeganeUtils.LOGGER.error("Stacktrace:", t);

            if (registry != null) {
                registry.error(entity);
            }
        }
    }

    @SuppressWarnings({"unchecked"})
    protected static void setContext(AbstractProvider<?> provider, IServerAccessor<Entity> accessor) {
        ((AbstractProvider<Entity>) provider).setContext(accessor.getWorld(), accessor.getTarget().getBlockPos(), accessor.getHitResult(), accessor.getPlayer(), accessor.getTarget());
    }

}
