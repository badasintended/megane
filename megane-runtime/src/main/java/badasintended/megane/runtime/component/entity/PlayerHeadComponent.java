package badasintended.megane.runtime.component.entity;

import mcp.mobius.waila.api.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;

import static badasintended.megane.util.MeganeUtils.config;

public class PlayerHeadComponent implements IEntityComponentProvider {

    private static final CompoundTag TAG = new CompoundTag();

    @Override
    public ItemStack getDisplayItem(IEntityAccessor accessor, IPluginConfig config) {
        Entity entity = accessor.getEntity();
        if (config().getPlayerHead() && entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            ItemStack stack = new ItemStack(Items.PLAYER_HEAD);
            stack.putSubTag("SkullOwner", NbtHelper.fromGameProfile(TAG, player.getGameProfile()));
            return stack;
        }
        return ItemStack.EMPTY;
    }

}
