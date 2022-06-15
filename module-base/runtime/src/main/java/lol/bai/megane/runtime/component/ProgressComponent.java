package lol.bai.megane.runtime.component;

import lol.bai.megane.runtime.util.Keys;
import lol.bai.megane.runtime.util.MeganeUtils;
import mcp.mobius.waila.api.ITooltipComponent;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ProgressComponent implements ITooltipComponent {

    private static final Identifier ARROW = MeganeUtils.id("textures/arrow.png");

    private final NbtCompound data;

    public ProgressComponent(NbtCompound data) {
        this.data = data;
    }

    @Override
    public int getWidth() {
        return (data.getInt(Keys.P_I_SIZE) + data.getInt(Keys.P_O_SIZE)) * 18 + 26;
    }

    @Override
    public int getHeight() {
        return 20;
    }

    @Override
    public void render(MatrixStack matrices, int x, int y, float delta) {
        int inputCount = data.getInt(Keys.P_I_SIZE);
        int outputCount = data.getInt(Keys.P_O_SIZE);
        int progressPixel = (int) (data.getInt(Keys.P_PERCENT) / 100F * 22);

        for (int i = 0; i < inputCount; i++) {
            ItemStack stack = new ItemStack(Registry.ITEM.get(data.getInt(Keys.P_I_ID + i)), data.getInt(Keys.P_I_COUNT + i));
            stack.setNbt(data.getCompound(Keys.P_I_NBT + i));
            if (stack.isEmpty()) {
                inputCount--;
                i--;
            } else {
                MeganeUtils.drawStack(stack, x + (i * 18), y + 1);
            }
        }

        MeganeUtils.drawTexture(matrices, ARROW, x + 2 + (inputCount * 18), y + 1, 22, 16, 0, 0.5F, 1, 1, 0xFFFFFF);
        MeganeUtils.drawTexture(matrices, ARROW, x + 2 + (inputCount * 18), y + 1, progressPixel, 16, 0, 0, progressPixel / 22F, 0.5F, 0xFFFFFF);

        for (int i = 0; i < outputCount; i++) {
            ItemStack stack = new ItemStack(Registry.ITEM.get(data.getInt(Keys.P_O_ID + i)), data.getInt(Keys.P_O_COUNT + i));
            stack.setNbt(data.getCompound(Keys.P_O_NBT + i));
            MeganeUtils.drawStack(stack, x + (inputCount * 18) + 26 + (i * 18), y + 1);
        }
    }

}
