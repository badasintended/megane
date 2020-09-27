package badasintended.megane.runtime.component;

import badasintended.megane.runtime.MeganeWaila;
import badasintended.megane.util.MeganeUtils;
import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.RenderableTextComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;

import java.util.List;

public class InventoryComponent implements IComponentProvider {

    public static final InventoryComponent INSTANCE = new InventoryComponent();

    @Override
    public void appendBody(List<Text> tooltip, IDataAccessor accessor, IPluginConfig config) {
        if (!MeganeUtils.config().inventory.isEnabled()) return;

        CompoundTag data = accessor.getServerData();
        if (data.getBoolean(MeganeUtils.key("hasInventory")) && data.getInt(MeganeUtils.key("percentage")) == 0 && data.getInt("progress") == 0) {
            tooltip.add(new RenderableTextComponent(MeganeWaila.INVENTORY, data));
        }
    }

}
