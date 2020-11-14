package badasintended.megane.runtime.component.entity;

import java.util.List;

import badasintended.megane.runtime.MeganeWaila;
import mcp.mobius.waila.api.IEntityAccessor;
import mcp.mobius.waila.api.RenderableTextComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;

import static badasintended.megane.util.MeganeUtils.config;
import static badasintended.megane.util.MeganeUtils.key;

public class EntityInventoryComponent extends EntityComponent {

    public EntityInventoryComponent() {
        super(() -> config().entityInventory);
    }

    @Override
    protected void append(List<Text> tooltip, IEntityAccessor accessor) {
        CompoundTag data = accessor.getServerData();
        if (data.getBoolean(key("hasInventory"))) {
            data.putInt(key("maxWidth"), config().entityInventory.getMaxWidth());
            data.putInt(key("maxHeight"), config().entityInventory.getMaxHeight());
            tooltip.add(new RenderableTextComponent(MeganeWaila.INVENTORY, data));
        }
    }

}
