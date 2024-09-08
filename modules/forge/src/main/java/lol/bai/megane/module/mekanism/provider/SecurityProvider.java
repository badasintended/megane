package lol.bai.megane.module.mekanism.provider;

import lol.bai.megane.module.mekanism.MeganeMekaninsm;
import mcp.mobius.waila.api.IBlockAccessor;
import mcp.mobius.waila.api.IBlockComponentProvider;
import mcp.mobius.waila.api.IData;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataReader;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IEntityAccessor;
import mcp.mobius.waila.api.IEntityComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.ITooltip;
import mcp.mobius.waila.api.component.PairComponent;
import mekanism.api.MekanismAPI;
import mekanism.api.security.SecurityMode;
import mekanism.api.text.APILang;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.util.MekanismUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;

public class SecurityProvider<T extends ICapabilityProvider> implements IBlockComponentProvider, IEntityComponentProvider, IDataProvider<T> {

    public static final ResourceLocation DATA = MeganeMekaninsm.id("security");

    private void appendBody(ITooltip tooltip, IDataReader data, IPluginConfig config) {
        if (!config.getBoolean(MeganeMekaninsm.CONFIG_SHOW_SECURITY)) return;

        var security = data.get(Data.class);
        if (security == null || security.mode == SecurityMode.PUBLIC) return;

        tooltip.addLine((switch (security.mode) {
            case PRIVATE -> APILang.PRIVATE;
            case TRUSTED -> APILang.TRUSTED;
            default -> throw new AssertionError();
        }).translate().withStyle(security.canAccess ? ChatFormatting.GREEN : ChatFormatting.RED));

        var owner = security.owner;
        if (owner != null) tooltip.addLine(new PairComponent(
            Component.translatable("megane.mekanism.owner"),
            Component.literal(owner)));
    }

    @Override
    public void appendBody(ITooltip tooltip, IBlockAccessor accessor, IPluginConfig config) {
        appendBody(tooltip, accessor.getData(), config);
    }

    @Override
    public void appendBody(ITooltip tooltip, IEntityAccessor accessor, IPluginConfig config) {
        appendBody(tooltip, accessor.getData(), config);
    }

    @Override
    public void appendData(IDataWriter data, IServerAccessor<T> accessor, IPluginConfig config) {
        if (!config.getBoolean(MeganeMekaninsm.CONFIG_SHOW_SECURITY)) return;

        var be = accessor.getTarget();
        be.getCapability(Capabilities.OWNER_OBJECT).ifPresent(owner -> {
            var api = MekanismAPI.getSecurityUtils();
            var security = be.getCapability(Capabilities.SECURITY_OBJECT).resolve().orElse(null);
            var securityMode = security == null ? SecurityMode.PUBLIC : api.getEffectiveSecurityMode(security, false);
            var canAccess = security == null || api.canAccessObject(accessor.getPlayer(), security);
            data.addImmediate(new Data(securityMode, MekanismUtils.getLastKnownUsername(owner.getOwnerUUID()), canAccess));
        });
    }

    public record Data(
        SecurityMode mode,
        @Nullable String owner,
        boolean canAccess
    ) implements IData {

        public Data(FriendlyByteBuf buf) {
            this(
                buf.readEnum(SecurityMode.class),
                buf.readNullable(FriendlyByteBuf::readUtf),
                buf.readBoolean());
        }

        @Override
        public void write(FriendlyByteBuf buf) {
            buf.writeEnum(mode);
            buf.writeNullable(owner, FriendlyByteBuf::writeUtf);
            buf.writeBoolean(canAccess);
        }

    }

}
