package badasintended.megane.runtime.renderer;

import java.awt.Dimension;

import mcp.mobius.waila.api.ICommonAccessor;
import mcp.mobius.waila.api.ITooltipRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static badasintended.megane.runtime.util.Keys.P_I_COUNT;
import static badasintended.megane.runtime.util.Keys.P_I_ID;
import static badasintended.megane.runtime.util.Keys.P_I_NBT;
import static badasintended.megane.runtime.util.Keys.P_I_SIZE;
import static badasintended.megane.runtime.util.Keys.P_O_COUNT;
import static badasintended.megane.runtime.util.Keys.P_O_ID;
import static badasintended.megane.runtime.util.Keys.P_O_NBT;
import static badasintended.megane.runtime.util.Keys.P_O_SIZE;
import static badasintended.megane.runtime.util.Keys.P_PERCENT;
import static badasintended.megane.runtime.util.RuntimeUtils.drawStack;
import static badasintended.megane.runtime.util.RuntimeUtils.drawTexture;
import static badasintended.megane.util.MeganeUtils.id;

public class ProgressRenderer implements ITooltipRenderer {

    private static final Identifier ARROW = id("textures/arrow.png");

    @Override
    public Dimension getSize(NbtCompound data, ICommonAccessor accessor) {
        return new Dimension((data.getInt(P_I_SIZE) + data.getInt(P_O_SIZE)) * 18 + 26, 20);
    }

    @Override
    public void draw(MatrixStack matrices, NbtCompound data, ICommonAccessor accessor, int x, int y) {
        int inputCount = data.getInt(P_I_SIZE);
        int outputCount = data.getInt(P_O_SIZE);
        int progressPixel = (int) (data.getInt(P_PERCENT) / 100F * 22);

        for (int i = 0; i < inputCount; i++) {
            ItemStack stack = new ItemStack(Registry.ITEM.get(data.getInt(P_I_ID + i)), data.getInt(P_I_COUNT + i));
            stack.setTag(data.getCompound(P_I_NBT + i));
            if (stack.isEmpty()) {
                inputCount--;
                i--;
            } else {
                drawStack(stack, x + (i * 18), y + 1);
            }
        }

        drawTexture(matrices, ARROW, x + 2 + (inputCount * 18), y + 1, 22, 16, 0, 0.5F, 1, 1, 0xFFFFFF);
        drawTexture(matrices, ARROW, x + 2 + (inputCount * 18), y + 1, progressPixel, 16, 0, 0, progressPixel / 22F, 0.5F, 0xFFFFFF);

        for (int i = 0; i < outputCount; i++) {
            ItemStack stack = new ItemStack(Registry.ITEM.get(data.getInt(P_O_ID + i)), data.getInt(P_O_COUNT + i));
            stack.setTag(data.getCompound(P_O_NBT + i));
            drawStack(stack, x + (inputCount * 18) + 26 + (i * 18), y + 1);
        }
    }

}
