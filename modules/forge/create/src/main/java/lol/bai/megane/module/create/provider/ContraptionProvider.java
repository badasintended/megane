package lol.bai.megane.module.create.provider;

import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import lol.bai.megane.module.create.mixin.AccessContraption;
import lol.bai.megane.module.create.mixin.AccessMountedStorageManager;
import mcp.mobius.waila.api.IData;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IEntityAccessor;
import mcp.mobius.waila.api.IEntityComponentProvider;
import mcp.mobius.waila.api.IModInfo;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.ITooltip;
import mcp.mobius.waila.api.ITooltipComponent;
import mcp.mobius.waila.api.IWailaConfig;
import mcp.mobius.waila.api.WailaConstants;
import mcp.mobius.waila.api.component.ItemComponent;
import mcp.mobius.waila.api.data.FluidData;
import mcp.mobius.waila.api.data.ItemData;
import mcp.mobius.waila.api.forge.ForgeFluidData;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

public class ContraptionProvider implements IEntityComponentProvider, IDataProvider<AbstractContraptionEntity> {

    public static final ResourceLocation CONFIG = new ResourceLocation("megane:create.contraption");
    public static final ResourceLocation CONTEXT = new ResourceLocation("megane:ctx.create.contraption");

    @Nullable
    private StructureBlockInfo lastInfo;
    private int lastUpdateId = 0;

    @Nullable
    private StructureBlockInfo getInfo(IEntityAccessor accessor) {
        if (accessor.getUpdateId() != lastUpdateId) {
            lastUpdateId = accessor.getUpdateId();

            AbstractContraptionEntity entity = accessor.getEntity();
            var origin = accessor.getEntityHitResult().getLocation();
            var viewVec = accessor.getRayCastDirection();
            var remaining = accessor.getRayCastMaxDistance() - accessor.getRayCastOrigin().distanceTo(origin);
            var max = origin.add(viewVec.x * remaining, viewVec.y * remaining, viewVec.z * remaining);

            var localOrigin = entity.toLocalVector(origin, accessor.getFrameTime());
            var localMax = entity.toLocalVector(max, accessor.getFrameTime());

            lastInfo = BlockGetter.traverseBlocks(localOrigin, localMax, Unit.INSTANCE, (unit, pos) -> {
                var info = entity.getContraption().getBlocks().get(pos);
                if (info == null) return null;

                var world = accessor.getWorld();
                var shape = info.state.getShape(world, pos);
                var hit = world.clipWithInteractionOverride(localOrigin, localMax, pos.immutable(), shape, info.state);
                if (hit == null) return null;

                return info;
            }, (unit) -> null);
        }

        return lastInfo;
    }

    @Override
    public void appendDataContext(IDataWriter ctx, IEntityAccessor accessor, IPluginConfig config) {
        var info = getInfo(accessor);
        if (info == null) return;

        ctx.addImmediate(new Context(info.pos));
    }

    @Override
    public @Nullable Entity getOverride(IEntityAccessor accessor, IPluginConfig config) {
        return getInfo(accessor) == null ? EMPTY_ENTITY : null;
    }

    @Override
    public @Nullable ITooltipComponent getIcon(IEntityAccessor accessor, IPluginConfig config) {
        var info = getInfo(accessor);
        if (info == null) return null;

        return new ItemComponent(info.state.getBlock());
    }

    @Override
    public void appendHead(ITooltip tooltip, IEntityAccessor accessor, IPluginConfig config) {
        var info = getInfo(accessor);
        if (info == null) return;

        var formatter = IWailaConfig.get().getFormatter();
        var block = info.state.getBlock();
        tooltip.setLine(WailaConstants.OBJECT_NAME_TAG, formatter.blockName(block.getName().getString()));
        if (config.getBoolean(WailaConstants.CONFIG_SHOW_REGISTRY)) {
            tooltip.setLine(WailaConstants.REGISTRY_NAME_TAG, formatter.registryName(ForgeRegistries.BLOCKS.getKey(block)));
        }
    }

    @Override
    public void appendBody(ITooltip tooltip, IEntityAccessor accessor, IPluginConfig config) {
        var info = getInfo(accessor);
        if (info == null) return;

        var data = accessor.getData();
        var ctx = data.get(Context.class);
        if (ctx == null) return;

        if (!ctx.localPos.equals(info.pos)) {
            data.invalidate(ItemData.class);
            data.invalidate(FluidData.class);
        }
    }

    @Override
    public void appendTail(ITooltip tooltip, IEntityAccessor accessor, IPluginConfig config) {
        if (!config.getBoolean(WailaConstants.CONFIG_SHOW_MOD_NAME)) return;

        var info = getInfo(accessor);
        if (info == null) return;

        tooltip.setLine(WailaConstants.MOD_NAME_TAG,
            IWailaConfig.get().getFormatter().modName(IModInfo.get(info.state.getBlock()).getName()));
    }

    @Override
    public void appendData(IDataWriter data, IServerAccessor<AbstractContraptionEntity> accessor, IPluginConfig config) {
        var ctx = accessor.getContext().get(Context.class);
        if (ctx == null) return;

        data.addImmediate(ctx);

        data.add(ItemData.class, res -> {
            var contraption = (AccessContraption) accessor.getTarget().getContraption();
            var manager = (AccessMountedStorageManager) contraption.megane_getStorageForSpawnPacket();
            var storage = manager.megane_storage().get(ctx.localPos);
            if (storage == null) return;

            var handler = storage.getItemHandler();
            res.add(ItemData.of(config)
                .getter(handler::getStackInSlot, handler.getSlots()));
        });

        data.add(FluidData.class, res -> {
            var contraption = (AccessContraption) accessor.getTarget().getContraption();
            var manager = (AccessMountedStorageManager) contraption.megane_getStorageForSpawnPacket();
            var storage = manager.megane_fluidStorage().get(ctx.localPos);
            if (storage == null) return;

            var tank = storage.getFluidHandler();
            res.add(ForgeFluidData.of(1)
                .add(tank.getFluidInTank(0), tank.getTankCapacity(0)));
        });
    }

    public record Context(
        BlockPos localPos
    ) implements IData {

        public Context(FriendlyByteBuf buf) {
            this(buf.readBlockPos());
        }

        @Override
        public void write(FriendlyByteBuf buf) {
            buf.writeBlockPos(localPos);
        }

    }

}
