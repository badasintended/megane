package lol.bai.megane.module.productivebees.provider;

import cy.jdkdigital.productivebees.common.block.entity.SolitaryNestBlockEntity;
import lol.bai.megane.module.productivebees.MeganeProductiveBees;
import mcp.mobius.waila.api.IBlockAccessor;
import mcp.mobius.waila.api.IBlockComponentProvider;
import mcp.mobius.waila.api.IData;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.ITooltip;
import mcp.mobius.waila.api.component.PairComponent;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class SolitaryNestProvider implements IBlockComponentProvider, IDataProvider<SolitaryNestBlockEntity> {

    public static final ResourceLocation DATA = MeganeProductiveBees.id("solitary_nest");

    private static final String TIMER = "%02d:%02d";

    private long lastCooldown;
    private long lastDataSync;

    @Override
    public void appendBody(ITooltip tooltip, IBlockAccessor accessor, IPluginConfig config) {
        var data = accessor.getData().get(Data.class);
        if (data == null || !config.getBoolean(MeganeProductiveBees.CONFIG_HIVE_COOLDOWN)) return;

        if (lastDataSync != accessor.getServerDataTime()) {
            lastDataSync = accessor.getServerDataTime();
            lastCooldown = data.tickCooldown;
            var delay = (System.currentTimeMillis() - lastDataSync) / 50;
            lastCooldown -= delay;
        }

        if (lastCooldown > 0) {
            var seconds = ((lastCooldown) / 20) + 1;
            var minutes = seconds / 60;
            seconds = seconds - (minutes * 60);
            tooltip.addLine(new PairComponent(
                Component.translatable("megane.productive_bees.hive.cooldown"),
                Component.literal(TIMER.formatted(minutes, seconds))));
        } else {
            tooltip.addLine(new PairComponent(
                Component.translatable("megane.productive_bees.hive.can_repopulate"),
                data.canRepopulate ? CommonComponents.GUI_YES : CommonComponents.GUI_NO));
        }

        lastCooldown--;
    }

    @Override
    public void appendData(IDataWriter data, IServerAccessor<SolitaryNestBlockEntity> accessor, IPluginConfig config) {
        var target = accessor.getTarget();

        if (config.getBoolean(MeganeProductiveBees.CONFIG_HIVE_OCCUPANTS)) data.add(AdvancedBeehiveProvider.OccupantsData.class, res -> {
            if (!target.isEmpty()) res.block();
        });

        if (config.getBoolean(MeganeProductiveBees.CONFIG_HIVE_COOLDOWN) && target.isEmpty()) {
            data.addImmediate(new Data(target.getNestTickCooldown(), target.canRepopulate()));
        }
    }

    public record Data(
        int tickCooldown,
        boolean canRepopulate
    ) implements IData {

        public Data(FriendlyByteBuf buf) {
            this(buf.readVarInt(), buf.readBoolean());
        }

        @Override
        public void write(FriendlyByteBuf buf) {
            buf.writeVarInt(tickCooldown);
            buf.writeBoolean(canRepopulate);
        }

    }

}
