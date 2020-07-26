package badasintended.megane.provider.component;

import badasintended.megane.Megane;
import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.RenderableTextComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;

import java.util.List;

import static badasintended.megane.MeganeUtils.key;

public class InventoryComponent implements IComponentProvider {

    public static final InventoryComponent INSTANCE = new InventoryComponent();

    private InventoryComponent() {
    }

    @Override
    public void appendBody(List<Text> tooltip, IDataAccessor accessor, IPluginConfig config) {
        if (config.get(Megane.INVENTORY)) {
            CompoundTag data = accessor.getServerData();
            if (data.getBoolean(key("hasInventory")) && data.getInt(key("percentage")) == 0 && data.getInt("progress") == 0) {
                tooltip.add(new RenderableTextComponent(Megane.INVENTORY, data));
            }
        }
    }

}
