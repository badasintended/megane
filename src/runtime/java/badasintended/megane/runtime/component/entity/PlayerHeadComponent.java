package badasintended.megane.runtime.component.entity;

import mcp.mobius.waila.api.IEntityAccessor;
import mcp.mobius.waila.api.IEntityComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;

import static badasintended.megane.util.MeganeUtils.config;

public class PlayerHeadComponent implements IEntityComponentProvider {

    private static final NbtCompound TAG = new NbtCompound();

    @Override
    public ItemStack getDisplayItem(IEntityAccessor accessor, IPluginConfig config) {
        Entity entity = accessor.getEntity();
        if (config().getPlayerHead() && entity instanceof PlayerEntity player) {
            ItemStack stack = new ItemStack(Items.PLAYER_HEAD);
            stack.putSubTag("SkullOwner", NbtHelper.writeGameProfile(TAG, player.getGameProfile()));
            return stack;
        }
        return ItemStack.EMPTY;
    }

}
