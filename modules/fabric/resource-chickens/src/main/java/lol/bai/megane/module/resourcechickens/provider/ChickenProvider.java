package lol.bai.megane.module.resourcechickens.provider;

import lol.bai.megane.module.resourcechickens.MeganeResourceChickens;
import mcp.mobius.waila.api.IData;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IEntityAccessor;
import mcp.mobius.waila.api.IEntityComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.ITooltip;
import mcp.mobius.waila.api.component.BarComponent;
import mcp.mobius.waila.api.component.PairComponent;
import mcp.mobius.waila.api.component.WrappedComponent;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import wallywhip.resourcechickens.ResourceChickens;
import wallywhip.resourcechickens.compat.modmenu.ModMenuManager;
import wallywhip.resourcechickens.entity.ResourceChickenEntity;
import wallywhip.resourcechickens.init.initChickenRegistry;

public class ChickenProvider implements IEntityComponentProvider, IDataProvider<ResourceChickenEntity> {

    public static final ResourceLocation DATA_TIMER = new ResourceLocation("megane:resource_chickens.chicken.timer");
    public static final ResourceLocation DATA_MUTATION = new ResourceLocation("megane:resource_chickens.chicken.mutation");

    @Override
    public void appendBody(ITooltip tooltip, IEntityAccessor accessor, IPluginConfig config) {
        var chicken = accessor.<ResourceChickenEntity>getEntity();

        if (chicken.getEntityData().get(ResourceChickenEntity.ANALYZED)) {
            if (config.getBoolean(MeganeResourceChickens.CONFIG_SHOW_GAIN)) tooltip.addLine(new PairComponent(
                Component.translatable("megane.resource_chickens.gain"),
                Component.literal(String.valueOf(chicken.getEntityData().get(ResourceChickenEntity.GAIN)))));

            if (config.getBoolean(MeganeResourceChickens.CONFIG_SHOW_GROWTH)) tooltip.addLine(new PairComponent(
                Component.translatable("megane.resource_chickens.growth"),
                Component.literal(String.valueOf(chicken.getEntityData().get(ResourceChickenEntity.GROWTH)))));

            if (config.getBoolean(MeganeResourceChickens.CONFIG_SHOW_STRENGTH)) tooltip.addLine(new PairComponent(
                Component.translatable("megane.resource_chickens.strength"),
                Component.literal(String.valueOf(chicken.getEntityData().get(ResourceChickenEntity.STRENGTH)))));
        }

        var timer = accessor.getData().get(Timer.class);
        if (config.getBoolean(MeganeResourceChickens.CONFIG_SHOW_DROP) && timer != null && ModMenuManager.getConfig().isAllowInWorldDrops() && !chicken.isBaby() && timer.eggLayTime != 0) {
            tooltip.addLine(new PairComponent(
                Component.translatable("megane.resource_chickens.next_drop"),
                Component.literal(ResourceChickens.formatTime(timer.eggTime))));
        }

        var mutation = accessor.getData().get(Mutation.class);
        if (config.getBoolean(MeganeResourceChickens.CONFIG_SHOW_CONVERSION) && mutation != null) {
            var target = initChickenRegistry.getChickenDataFromName(mutation.type);
            if (target != null) {
                tooltip.addLine(new PairComponent(
                    Component.translatable("megane.resource_chickens.conversion"),
                    target.displayName));

                tooltip.addLine(new PairComponent(
                    new WrappedComponent(Component.translatable("megane.resource_chickens.progress")),
                    new BarComponent((float) mutation.count / target.conversionRequired, 0xff89710f, Component.literal(mutation.count + "/" + target.conversionRequired))));
            }
        }
    }

    @Override
    public void appendData(IDataWriter data, IServerAccessor<ResourceChickenEntity> accessor, IPluginConfig config) {
        var chicken = accessor.getTarget();

        if (config.getBoolean(MeganeResourceChickens.CONFIG_SHOW_DROP) && ModMenuManager.getConfig().isAllowInWorldDrops()) {
            data.addImmediate(new Timer(chicken.chickenData.eggLayTime, chicken.eggTime));
        }

        if (config.getBoolean(MeganeResourceChickens.CONFIG_SHOW_CONVERSION) && chicken.conversionProgress != 0) {
            data.addImmediate(new Mutation(chicken.conversionProgress, chicken.chickenDataCONV.ID));
        }
    }

    public record Timer(
        int eggLayTime,
        int eggTime
    ) implements IData {

        public Timer(FriendlyByteBuf buf) {
            this(buf.readVarInt(), buf.readVarInt());
        }

        @Override
        public void write(FriendlyByteBuf buf) {
            buf.writeVarInt(eggLayTime);
            buf.writeVarInt(eggTime);
        }

    }

    public record Mutation(
        int count,
        String type
    ) implements IData {

        public Mutation(FriendlyByteBuf buf) {
            this(buf.readVarInt(), buf.readUtf());
        }

        @Override
        public void write(FriendlyByteBuf buf) {
            buf.writeVarInt(count);
            buf.writeUtf(type);
        }

    }

}
