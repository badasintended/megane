package badasintended.megane.renderer;

import mcp.mobius.waila.api.ICommonAccessor;
import mcp.mobius.waila.api.ITooltipRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.nbt.CompoundTag;

import java.awt.*;

import static badasintended.megane.MeganeUtils.key;

public class EntityInfoRenderer implements ITooltipRenderer {

    @Override
    public Dimension getSize(CompoundTag data, ICommonAccessor accessor) {
        return null;
    }

    @Override
    public void draw(MatrixStack matrices, CompoundTag data, ICommonAccessor accessor, int x, int y) {
        float health = data.getInt(key("health"));
        int armor = data.getInt(key("armor"));

        //drawTexture(matrices, DrawableHelper.GUI_ICONS_TEXTURE);
    }

}
