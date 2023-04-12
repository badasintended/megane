package lol.bai.megane.runtime.data.block;

import java.util.function.Supplier;

import lol.bai.megane.api.provider.AbstractProvider;
import lol.bai.megane.runtime.config.MeganeConfig;
import lol.bai.megane.runtime.registry.Registry;
import lol.bai.megane.runtime.util.MeganeUtils;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.IServerDataProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;

public abstract class BlockData implements IServerDataProvider<BlockEntity> {

    private static final Text ERROR_TEXT = Text.literal("Something went wrong when retrieving data for this block").styled(style -> style
        .withColor(Formatting.RED)
        .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal("Click me to open an issue at GitHub")))
        .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, MeganeUtils.ISSUE_URL)));

    private final Registry<?> registry;
    private final Supplier<MeganeConfig.Base> baseConfig;

    public BlockData(Supplier<MeganeConfig.Base> baseConfig) {
        this(null, baseConfig);
    }

    public BlockData(Registry<?> registry, Supplier<MeganeConfig.Base> baseConfig) {
        this.registry = registry;
        this.baseConfig = baseConfig;
    }

    abstract void append(NbtCompound data, IServerAccessor<BlockEntity> accessor);

    @Override
    public void appendServerData(NbtCompound data, IServerAccessor<BlockEntity> accessor, IPluginConfig config) {
        BlockEntity blockEntity = accessor.getTarget();

        if (!baseConfig.get().isEnabled() || baseConfig.get().getBlacklist().contains(Registries.BLOCK.getId(blockEntity.getCachedState().getBlock()))) {
            return;
        }

        try {
            append(data, accessor);
        } catch (Throwable t) {
            BlockPos pos = blockEntity.getPos();
            accessor.getPlayer().sendMessage(ERROR_TEXT);
            MeganeUtils.LOGGER.error("Something went wrong when retrieving data for {} at ({}, {}, {})", blockEntity.getClass().getName(), pos.getX(), pos.getY(), pos.getZ());
            if (!MeganeUtils.config().getCatchServerErrors()) {
                throw t;
            }
            MeganeUtils.LOGGER.error("Stacktrace:", t);

            if (registry != null) {
                registry.error(blockEntity);
            }
        }
    }

    @SuppressWarnings({"unchecked"})
    protected static void setContext(AbstractProvider<?> provider, IServerAccessor<BlockEntity> accessor) {
        ((AbstractProvider<BlockEntity>) provider).setContext(accessor.getWorld(), accessor.getTarget().getPos(), accessor.getHitResult(), accessor.getPlayer(), accessor.getTarget());
    }

}
