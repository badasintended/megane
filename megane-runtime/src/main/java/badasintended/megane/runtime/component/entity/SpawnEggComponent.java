package badasintended.megane.runtime.component.entity;

import mcp.mobius.waila.api.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;

import static badasintended.megane.util.MeganeUtils.config;

public class SpawnEggComponent implements IEntityComponentProvider {

    @Override
    public ItemStack getDisplayItem(IEntityAccessor accessor, IPluginConfig config) {
        if (!config().getSpawnEgg()) return ItemStack.EMPTY;
        return new ItemStack(SpawnEggItem.forEntity(accessor.getEntity().getType()));
    }

}
