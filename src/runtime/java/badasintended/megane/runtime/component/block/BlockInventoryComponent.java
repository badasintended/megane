package badasintended.megane.runtime.component.block;

import badasintended.megane.runtime.Megane;
import mcp.mobius.waila.api.IBlockAccessor;
import mcp.mobius.waila.api.ITooltip;
import net.minecraft.nbt.NbtCompound;

import static badasintended.megane.runtime.util.Keys.I_HAS;
import static badasintended.megane.runtime.util.Keys.I_MAX_H;
import static badasintended.megane.runtime.util.Keys.I_MAX_W;
import static badasintended.megane.runtime.util.Keys.P_PERCENT;
import static badasintended.megane.util.MeganeUtils.config;

public class BlockInventoryComponent extends BlockComponent {

    public BlockInventoryComponent() {
        super(() -> config().inventory);
    }

    @Override
    protected void append(ITooltip tooltip, IBlockAccessor accessor) {
        NbtCompound data = accessor.getServerData();
        if (data.getBoolean(I_HAS) && data.getInt(P_PERCENT) == 0 && data.getInt("progress") == 0 && !config().progress.isShowWhenZero()) {
            data.putInt(I_MAX_W, config().inventory.getMaxWidth());
            data.putInt(I_MAX_H, config().inventory.getMaxHeight());
            tooltip.addDrawable(Megane.INVENTORY, data);
        }
    }

}
