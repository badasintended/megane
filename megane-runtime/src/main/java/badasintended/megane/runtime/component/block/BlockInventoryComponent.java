package badasintended.megane.runtime.component.block;

import badasintended.megane.runtime.MeganeWaila;
import mcp.mobius.waila.api.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;

import java.util.List;

import static badasintended.megane.util.MeganeUtils.config;
import static badasintended.megane.util.MeganeUtils.key;

public class BlockInventoryComponent extends BlockComponent {

    public BlockInventoryComponent() {
        super(() -> config().inventory);
    }

    @Override
    protected void append(List<Text> tooltip, IDataAccessor accessor, IPluginConfig config) {
        CompoundTag data = accessor.getServerData();
        if (data.getBoolean(key("hasInventory")) && data.getInt(key("percentage")) == 0 && data.getInt("progress") == 0 && !config().progress.isShowWhenZero()) {
            data.putInt(key("maxWidth"), config().inventory.getMaxWidth());
            data.putInt(key("maxHeight"), config().inventory.getMaxHeight());
            tooltip.add(new RenderableTextComponent(MeganeWaila.INVENTORY, data));
        }
    }

}
