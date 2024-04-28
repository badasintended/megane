package lol.bai.megane.module.resourcechickens.provider;

import lol.bai.megane.module.resourcechickens.MeganeResourceChickens;
import mcp.mobius.waila.api.IBlockAccessor;
import mcp.mobius.waila.api.IBlockComponentProvider;
import mcp.mobius.waila.api.IData;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.ITooltip;
import mcp.mobius.waila.api.IWailaConfig;
import mcp.mobius.waila.api.WailaConstants;
import mcp.mobius.waila.api.component.BarComponent;
import mcp.mobius.waila.api.component.PairComponent;
import mcp.mobius.waila.api.component.WrappedComponent;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import wallywhip.resourcechickens.ResourceChickens;
import wallywhip.resourcechickens.blocks.NestTileEntity;

public class NestProvider implements IBlockComponentProvider, IDataProvider<NestTileEntity> {

    public static final ResourceLocation DATA = new ResourceLocation("megane:resource_chickens.nest");

    @Override
    public void appendHead(ITooltip tooltip, IBlockAccessor accessor, IPluginConfig config) {
        var nest = accessor.<NestTileEntity>getBlockEntity();
        if (nest == null) return;
        if (nest.entityCaptured == null) return;

        var name = nest.entityCustomName != null
            ? nest.entityCustomName
            : nest.chickenData.displayName;

        tooltip.setLine(WailaConstants.OBJECT_NAME_TAG, IWailaConfig.get().getFormatter().blockName(name.getString()));
    }

    @Override
    public void appendBody(ITooltip tooltip, IBlockAccessor accessor, IPluginConfig config) {
        var nest = accessor.<NestTileEntity>getBlockEntity();
        if (nest == null) return;

        var data = accessor.getData().get(Data.class);
        if (data == null) return;

        if (nest.entityCaptured != null) {
            if (nest.analyzed) {
                if (config.getBoolean(MeganeResourceChickens.CONFIG_SHOW_GAIN)) tooltip.addLine(new PairComponent(
                    Component.translatable("megane.resource_chickens.gain"),
                    Component.literal(String.valueOf(nest.chickenGain))));

                if (config.getBoolean(MeganeResourceChickens.CONFIG_SHOW_GROWTH)) tooltip.addLine(new PairComponent(
                    Component.translatable("megane.resource_chickens.growth"),
                    Component.literal(String.valueOf(nest.chickenGrowth))));

                if (config.getBoolean(MeganeResourceChickens.CONFIG_SHOW_STRENGTH)) tooltip.addLine(new PairComponent(
                    Component.translatable("megane.resource_chickens.strength"),
                    Component.literal(String.valueOf(nest.chickenStrength))));
            }

            if (data.chickenAge < 0) {
                if (config.getBoolean(MeganeResourceChickens.CONFIG_SHOW_GROW)) tooltip.addLine(new PairComponent(
                    Component.translatable("tooltip.waila.timer.grow"),
                    Component.literal(ResourceChickens.formatTime(-data.chickenAge))));
            } else if (nest.chickenData.eggLayTime != 0 && !data.requiresSeeds) {
                if (config.getBoolean(MeganeResourceChickens.CONFIG_SHOW_DROP)) tooltip.addLine(new PairComponent(
                    Component.translatable("megane.resource_chickens.next_drop"),
                    Component.literal(ResourceChickens.formatTime(data.eggTime))));
            }
        }

        if (config.getBoolean(MeganeResourceChickens.CONFIG_SHOW_NEST_FOOD)) tooltip.addLine(new PairComponent(
            new WrappedComponent(Component.translatable("tip.resourcechickens.food")),
            new BarComponent(nest.foodLevel / 100f, 0xffffff55, Component.literal(nest.foodLevel + "/100"))
        ));

    }

    @Override
    public void appendData(IDataWriter data, IServerAccessor<NestTileEntity> accessor, IPluginConfig config) {
        var nest = accessor.getTarget();

        data.addImmediate(new Data(
            nest.chickenAge, nest.eggLayTime,
            ResourceChickens.CONFIGURATION.seededChamber.get() && nest.foodLevel == 0));
    }

    public record Data(
        int chickenAge,
        int eggTime,
        boolean requiresSeeds
    ) implements IData {

        public Data(FriendlyByteBuf buf) {
            this(buf.readVarInt(), buf.readVarInt(), buf.readBoolean());
        }

        @Override
        public void write(FriendlyByteBuf buf) {
            buf.writeVarInt(chickenAge);
            buf.writeVarInt(eggTime);
            buf.writeBoolean(requiresSeeds);
        }

    }


}
