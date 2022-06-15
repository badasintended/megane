package lol.bai.megane.runtime.provider.block;

import lol.bai.megane.runtime.component.InventoryComponent;
import mcp.mobius.waila.api.IBlockAccessor;
import mcp.mobius.waila.api.ITooltip;
import net.minecraft.nbt.NbtCompound;

import static lol.bai.megane.runtime.util.Keys.I_HAS;
import static lol.bai.megane.runtime.util.Keys.P_PERCENT;
import static lol.bai.megane.runtime.util.MeganeUtils.config;

public class BlockInventoryComponentProvider extends BlockComponentProvider {

    public BlockInventoryComponentProvider() {
        super(() -> config().inventory);
    }

    @Override
    protected void append(ITooltip tooltip, IBlockAccessor accessor) {
        NbtCompound data = accessor.getServerData();
        if (data.getBoolean(I_HAS) && data.getInt(P_PERCENT) == 0 && data.getInt("progress") == 0 && !config().progress.isShowWhenZero()) {
            tooltip.addLine(new InventoryComponent(data, config().inventory.getMaxWidth(), config().inventory.getMaxHeight()));
        }
    }

}
