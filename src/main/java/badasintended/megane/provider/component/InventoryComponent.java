package badasintended.megane.provider.component;

import badasintended.megane.PluginMegane;
import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.RenderableTextComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;

import java.util.List;

import static badasintended.megane.Utils.key;

public class InventoryComponent implements IComponentProvider {

    public static final InventoryComponent INSTANCE = new InventoryComponent();

    private InventoryComponent() {
    }

    @Override
    public void appendBody(List<Text> tooltip, IDataAccessor accessor, IPluginConfig config) {
        if (config.get(PluginMegane.Config.INVENTORY)) {
            CompoundTag data = accessor.getServerData();
            if (data.getBoolean(key("hasInventory"))) {
                tooltip.add(new RenderableTextComponent(PluginMegane.Render.INVENTORY, data));
            }
        }
    }

}
