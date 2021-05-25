package badasintended.megane.runtime.component.entity;

import java.util.List;

import badasintended.megane.runtime.Megane;
import mcp.mobius.waila.api.IEntityAccessor;
import mcp.mobius.waila.api.RenderableTextComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;

import static badasintended.megane.runtime.util.Keys.I_HAS;
import static badasintended.megane.runtime.util.Keys.I_MAX_H;
import static badasintended.megane.runtime.util.Keys.I_MAX_W;
import static badasintended.megane.util.MeganeUtils.config;

public class EntityInventoryComponent extends EntityComponent {

    public EntityInventoryComponent() {
        super(() -> config().entityInventory);
    }

    @Override
    protected void append(List<Text> tooltip, IEntityAccessor accessor) {
        CompoundTag data = accessor.getServerData();
        if (data.getBoolean(I_HAS)) {
            data.putInt(I_MAX_W, config().entityInventory.getMaxWidth());
            data.putInt(I_MAX_H, config().entityInventory.getMaxHeight());
            tooltip.add(new RenderableTextComponent(Megane.INVENTORY, data));
        }
    }

}
