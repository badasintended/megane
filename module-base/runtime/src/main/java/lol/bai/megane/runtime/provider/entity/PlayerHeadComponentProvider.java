package lol.bai.megane.runtime.provider.entity;

import mcp.mobius.waila.api.IEntityAccessor;
import mcp.mobius.waila.api.IEntityComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.ITooltipComponent;
import mcp.mobius.waila.api.component.ItemComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;

import static lol.bai.megane.runtime.util.MeganeUtils.config;

public class PlayerHeadComponentProvider implements IEntityComponentProvider {

    private static final ItemComponent COMPONENT = new ItemComponent(Items.PLAYER_HEAD);
    private static final NbtCompound TAG = new NbtCompound();

    @Override
    public ITooltipComponent getIcon(IEntityAccessor accessor, IPluginConfig config) {
        Entity entity = accessor.getEntity();
        if (config().getPlayerHead() && entity instanceof PlayerEntity player) {
            COMPONENT.stack.setSubNbt("SkullOwner", NbtHelper.writeGameProfile(TAG, player.getGameProfile()));
            return COMPONENT;
        }
        return null;
    }

}
