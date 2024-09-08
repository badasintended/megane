package lol.bai.megane.module.productivebees.provider;

import cy.jdkdigital.productivebees.common.block.entity.JarBlockEntity;
import cy.jdkdigital.productivebees.common.item.BeeCage;
import lol.bai.megane.module.productivebees.MeganeProductiveBees;
import mcp.mobius.waila.api.IBlockAccessor;
import mcp.mobius.waila.api.IBlockComponentProvider;
import mcp.mobius.waila.api.IData;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IModInfo;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.ITooltip;
import mcp.mobius.waila.api.IWailaConfig;
import mcp.mobius.waila.api.WailaConstants;
import mcp.mobius.waila.api.data.ItemData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

public class JarProvider implements IBlockComponentProvider, IDataProvider<JarBlockEntity> {

    public static final ResourceLocation DATA = MeganeProductiveBees.id("jar");

    private int updateId = 0;
    private @Nullable Entity entity;

    private @Nullable Entity getEntity(IBlockAccessor accessor) {
        if (updateId != accessor.getUpdateId()) {
            updateId = accessor.getUpdateId();
            entity = null;

            var data = accessor.getData().get(Data.class);
            var jar = accessor.<JarBlockEntity>getBlockEntity();

            if (data != null && jar != null) {
                entity = jar.getCachedEntity(data.beeStack);
            }
        }

        return entity;
    }

    @Override
    public void appendHead(ITooltip tooltip, IBlockAccessor accessor, IPluginConfig config) {
        var entity = getEntity(accessor);
        if (entity == null) return;

        var formatter = IWailaConfig.get().getFormatter();
        tooltip.setLine(WailaConstants.OBJECT_NAME_TAG, formatter.blockName(entity.getName().getString()));
        if (config.getBoolean(WailaConstants.CONFIG_SHOW_REGISTRY)) {
            tooltip.setLine(WailaConstants.REGISTRY_NAME_TAG, formatter.registryName(ForgeRegistries.ENTITY_TYPES.getKey(entity.getType())));
        }
    }

    @Override
    public void appendTail(ITooltip tooltip, IBlockAccessor accessor, IPluginConfig config) {
        var entity = getEntity(accessor);
        if (entity == null) return;

        tooltip.setLine(WailaConstants.MOD_NAME_TAG, IWailaConfig.get().getFormatter().modName(IModInfo.get(entity).getName()));
    }

    @Override
    public void appendData(IDataWriter data, IServerAccessor<JarBlockEntity> accessor, IPluginConfig config) {
        data.blockAll(ItemData.class);

        var jar = accessor.getTarget();
        jar.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(inv -> {
            var stack = inv.getStackInSlot(0);
            if (BeeCage.isFilled(stack)) {
                data.addImmediate(new Data(stack));
            }
        });
    }

    public record Data(
        ItemStack beeStack
    ) implements IData {

        public Data(FriendlyByteBuf buf) {
            this(buf.readItem());
        }

        @Override
        public void write(FriendlyByteBuf buf) {
            buf.writeItem(beeStack);
        }

    }

}
