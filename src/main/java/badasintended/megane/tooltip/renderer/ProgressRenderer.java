package badasintended.megane.tooltip.renderer;

import mcp.mobius.waila.api.ICommonAccessor;
import mcp.mobius.waila.api.ITooltipRenderer;
import mcp.mobius.waila.overlay.DisplayUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;

import java.awt.*;

import static badasintended.megane.MeganeUtils.*;

public class ProgressRenderer implements ITooltipRenderer {

    private static final Identifier ARROW = id("textures/arrow.png");

    public static final ProgressRenderer INSTANCE = new ProgressRenderer();

    private ProgressRenderer() {
    }

    @Override
    public Dimension getSize(CompoundTag data, ICommonAccessor accessor) {
        return new Dimension((data.getInt(key("inputCount")) + data.getInt(key("outputCount"))) * 18 + 26, 18);
    }

    @Override
    public void draw(MatrixStack matrices, CompoundTag data, ICommonAccessor accessor, int x, int y) {
        int inputCount = data.getInt(key("inputCount"));
        int outputCount = data.getInt(key("outputCount"));
        int progressPixel = (int) (data.getInt(key("percentage")) / 100F * 22);

        for (int i = 0; i < inputCount; i++) {
            DisplayUtil.renderStack(matrices, x + (i * 18), y + 1, ItemStack.fromTag(data.getCompound(key("input" + i))));
        }

        drawTexture(matrices, ARROW, x + 2 + (inputCount * 18), y + 1, 22, 16, 0, 0.5F, 1, 1, 0xFFFFFFFF);
        drawTexture(matrices, ARROW, x + 2 + (inputCount * 18), y + 1, progressPixel, 16, 0, 0, progressPixel / 22F, 0.5F, 0xFFFFFFFF);

        for (int i = 0; i < outputCount; i++) {
            DisplayUtil.renderStack(matrices, x + (inputCount * 18) + 26 + (i * 18), y + 1, ItemStack.fromTag(data.getCompound(key("output" + i))));
        }
    }

}
