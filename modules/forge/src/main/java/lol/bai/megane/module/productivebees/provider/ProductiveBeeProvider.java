package lol.bai.megane.module.productivebees.provider;

import cy.jdkdigital.productivebees.common.entity.bee.ProductiveBee;
import cy.jdkdigital.productivebees.util.BeeAttributes;
import cy.jdkdigital.productivebees.util.ColorUtil;
import lol.bai.megane.module.productivebees.MeganeProductiveBees;
import mcp.mobius.waila.api.IData;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IEntityAccessor;
import mcp.mobius.waila.api.IEntityComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.ITooltip;
import mcp.mobius.waila.api.component.PairComponent;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ProductiveBeeProvider implements IEntityComponentProvider, IDataProvider<ProductiveBee> {

    public static final ResourceLocation DATA = MeganeProductiveBees.id("bee");

    @Override
    public void appendBody(ITooltip tooltip, IEntityAccessor accessor, IPluginConfig config) {
        var data = accessor.getData().get(Data.class);
        if (data == null) return;

        if (config.getBoolean(MeganeProductiveBees.CONFIG_BEE_TYPE)) tooltip.addLine(new PairComponent(
            Component.translatable("megane.productive_bees.bee.type"),
            Component.translatable("productivebees.information.attribute.type." + data.type).withStyle(ColorUtil.getColor(data.type))));

        if (config.getBoolean(MeganeProductiveBees.CONFIG_BEE_PRODUCTIVITY)) tooltip.addLine(new PairComponent(
            Component.translatable("megane.productive_bees.bee.productivity"),
            Component.translatable(BeeAttributes.keyMap.get(BeeAttributes.PRODUCTIVITY).get(data.productivity)).withStyle(ColorUtil.getColor(data.productivity))));

        if (config.getBoolean(MeganeProductiveBees.CONFIG_BEE_TOLERANCE)) tooltip.addLine(new PairComponent(
            Component.translatable("megane.productive_bees.bee.tolerance"),
            Component.translatable(BeeAttributes.keyMap.get(BeeAttributes.WEATHER_TOLERANCE).get(data.weatherTolerance)).withStyle(ColorUtil.getColor(data.weatherTolerance))));

        if (config.getBoolean(MeganeProductiveBees.CONFIG_BEE_BEHAVIOR)) tooltip.addLine(new PairComponent(
            Component.translatable("megane.productive_bees.bee.behavior"),
            Component.translatable(BeeAttributes.keyMap.get(BeeAttributes.BEHAVIOR).get(data.behavior)).withStyle(ColorUtil.getColor(data.behavior))));

        if (config.getBoolean(MeganeProductiveBees.CONFIG_BEE_ENDURANCE)) tooltip.addLine(new PairComponent(
            Component.translatable("megane.productive_bees.bee.endurance"),
            Component.translatable(BeeAttributes.keyMap.get(BeeAttributes.ENDURANCE).get(data.endurance)).withStyle(ColorUtil.getColor(data.endurance))));

        if (config.getBoolean(MeganeProductiveBees.CONFIG_BEE_TEMPER)) tooltip.addLine(new PairComponent(
            Component.translatable("megane.productive_bees.bee.temper"),
            Component.translatable(BeeAttributes.keyMap.get(BeeAttributes.TEMPER).get(data.temper)).withStyle(ColorUtil.getColor(data.temper))));
    }

    @Override
    public void appendData(IDataWriter data, IServerAccessor<ProductiveBee> accessor, IPluginConfig config) {
        var bee = accessor.getTarget();
        data.addImmediate(new Data(
            bee.getAttributeValue(BeeAttributes.TYPE),
            bee.getAttributeValue(BeeAttributes.PRODUCTIVITY),
            bee.getAttributeValue(BeeAttributes.WEATHER_TOLERANCE),
            bee.getAttributeValue(BeeAttributes.BEHAVIOR),
            bee.getAttributeValue(BeeAttributes.ENDURANCE),
            bee.getAttributeValue(BeeAttributes.TEMPER)));
    }

    public record Data(
        String type,
        int productivity,
        int weatherTolerance,
        int behavior,
        int endurance,
        int temper
    ) implements IData {

        public Data(FriendlyByteBuf buf) {
            this(
                buf.readUtf(),
                buf.readVarInt(),
                buf.readVarInt(),
                buf.readVarInt(),
                buf.readVarInt(),
                buf.readVarInt());
        }

        @Override
        public void write(FriendlyByteBuf buf) {
            buf.writeUtf(type);
            buf.writeVarInt(productivity);
            buf.writeVarInt(weatherTolerance);
            buf.writeVarInt(behavior);
            buf.writeVarInt(endurance);
            buf.writeVarInt(temper);
        }

    }

}
