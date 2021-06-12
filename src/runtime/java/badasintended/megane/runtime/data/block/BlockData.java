package badasintended.megane.runtime.data.block;

import java.util.function.Supplier;

import badasintended.megane.config.MeganeConfig;
import badasintended.megane.runtime.registry.Registry;
import mcp.mobius.waila.api.IServerDataProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static badasintended.megane.util.MeganeUtils.ISSUE_URL;
import static badasintended.megane.util.MeganeUtils.LOGGER;
import static badasintended.megane.util.MeganeUtils.config;
import static net.minecraft.util.registry.Registry.BLOCK;

public abstract class BlockData implements IServerDataProvider<BlockEntity> {

    private static final Text ERROR_TEXT = new LiteralText("Something went wrong when retrieving data for this block").styled(style -> style
        .withColor(Formatting.RED)
        .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new LiteralText("Click me to open an issue at GitHub")))
        .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, ISSUE_URL)));

    private final Registry<?> registry;
    private final Supplier<MeganeConfig.Base> baseConfig;

    public BlockData(Supplier<MeganeConfig.Base> baseConfig) {
        this(null, baseConfig);
    }

    public BlockData(Registry<?> registry, Supplier<MeganeConfig.Base> baseConfig) {
        this.registry = registry;
        this.baseConfig = baseConfig;
    }

    abstract void append(NbtCompound data, ServerPlayerEntity player, World world, BlockEntity blockEntity);

    @Override
    public final void appendServerData(NbtCompound data, ServerPlayerEntity player, World world, BlockEntity blockEntity) {
        if (!baseConfig.get().isEnabled() || baseConfig.get().getBlacklist().contains(BLOCK.getId(blockEntity.getCachedState().getBlock()))) {
            return;
        }

        try {
            append(data, player, world, blockEntity);
        } catch (Throwable t) {
            BlockPos pos = blockEntity.getPos();
            player.sendSystemMessage(ERROR_TEXT, Util.NIL_UUID);
            LOGGER.error("Something went wrong when retrieving data for {} at ({}, {}, {})", blockEntity.getClass().getName(), pos.getX(), pos.getY(), pos.getZ());
            if (!config().getCatchServerErrors()) {
                throw t;
            }
            LOGGER.error("Stacktrace:", t);

            if (registry != null) {
                registry.error(blockEntity);
            }
        }
    }

}
