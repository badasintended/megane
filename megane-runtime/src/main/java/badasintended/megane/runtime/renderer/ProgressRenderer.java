package badasintended.megane.runtime.renderer;

import mcp.mobius.waila.api.ICommonAccessor;
import mcp.mobius.waila.api.ITooltipRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;

import java.awt.*;

import static badasintended.megane.util.MeganeUtils.*;

public class ProgressRenderer implements ITooltipRenderer {

    private static ProgressRenderer instance = null;

    private static final Identifier ARROW = id("textures/arrow.png");

    public ProgressRenderer() {
        instance = this;
    }

    public static ProgressRenderer getInstance() {
        return instance;
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
            ItemStack stack = ItemStack.fromTag(data.getCompound(key("input" + i)));
            if (stack.isEmpty()) {
                inputCount--;
                i--;
            } else {
                drawStack(stack, x + (i * 18), y + 1);
            }
        }

        drawTexture(matrices, ARROW, x + 2 + (inputCount * 18), y + 1, 22, 16, 0, 0.5F, 1, 1, 0xFFFFFFFF);
        drawTexture(matrices, ARROW, x + 2 + (inputCount * 18), y + 1, progressPixel, 16, 0, 0, progressPixel / 22F, 0.5F, 0xFFFFFFFF);

        for (int i = 0; i < outputCount; i++) {
            drawStack(ItemStack.fromTag(data.getCompound(key("output" + i))), x + (inputCount * 18) + 26 + (i * 18), y + 1);
        }
    }

}
