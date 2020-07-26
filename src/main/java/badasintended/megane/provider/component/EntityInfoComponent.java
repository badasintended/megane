package badasintended.megane.provider.component;

import badasintended.megane.Megane;
import mcp.mobius.waila.api.IEntityAccessor;
import mcp.mobius.waila.api.IEntityComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import net.minecraft.text.Text;

import java.util.List;

import static badasintended.megane.MeganeUtils.key;
import static badasintended.megane.MeganeUtils.tl;

public class EntityInfoComponent implements IEntityComponentProvider {

    public static final EntityInfoComponent INSTANCE = new EntityInfoComponent();

    private EntityInfoComponent() {
    }

    @Override
    public void appendBody(List<Text> tooltip, IEntityAccessor accessor, IPluginConfig config) {
        if (config.get(Megane.ENTITY_INFO)) {
            tooltip.add(tl("armor", accessor.getServerData().getInt(key("armor"))));
        }
    }

}
