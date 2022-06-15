package lol.bai.megane.runtime.provider.entity;

import lol.bai.megane.runtime.component.InventoryComponent;
import mcp.mobius.waila.api.IEntityAccessor;
import mcp.mobius.waila.api.ITooltip;
import net.minecraft.nbt.NbtCompound;

import static lol.bai.megane.runtime.util.Keys.I_HAS;
import static lol.bai.megane.runtime.util.MeganeUtils.config;

public class EntityInventoryComponentProvider extends EntityComponentProvider {

    public EntityInventoryComponentProvider() {
        super(() -> config().entityInventory);
    }

    @Override
    protected void append(ITooltip tooltip, IEntityAccessor accessor) {
        NbtCompound data = accessor.getServerData();
        if (data.getBoolean(I_HAS)) {
            tooltip.addLine(new InventoryComponent(data, config().entityInventory.getMaxWidth(), config().entityInventory.getMaxHeight()));
        }
    }

}
