package lol.bai.megane.runtime.component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import lol.bai.megane.runtime.util.Keys;
import lol.bai.megane.runtime.util.MeganeUtils;
import mcp.mobius.waila.api.ITooltipComponent;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;

public class InventoryComponent implements ITooltipComponent {

    private final List<ItemStack> stacks = new ArrayList<>();

    private final int maxWidth, maxHeight;
    private final int width, height;

    public InventoryComponent(NbtCompound data, int maxWidth, int maxHeight) {
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;

        int size = data.getInt(Keys.I_SIZE);
        boolean showCount = data.getBoolean(Keys.I_SHOW);

        stacks.clear();
        for (int i = 0; i < size; i++) {
            Item item = Registries.ITEM.get(data.getInt(Keys.I_ID + i));
            int count = showCount ? data.getInt(Keys.I_COUNT + i) : 1;
            NbtCompound nbt = (NbtCompound) data.get(Keys.I_NBT + i);
            if (count <= 0)
                continue;
            ItemStack stack = new ItemStack(item, count);
            stack.setNbt(nbt);
            Optional<ItemStack> optional = stacks
                .stream()
                .filter(j -> j.getItem() == stack.getItem() && j.getOrCreateNbt().equals(nbt))
                .findFirst();
            if (optional.isPresent()) {
                if (showCount) {
                    optional.get().increment(stack.getCount());
                }
            } else {
                stacks.add(stack);
            }
        }

        stacks.sort(Comparator.comparingInt(ItemStack::getCount).reversed());

        if (stacks.size() == 0) {
            width = 0;
            height = 0;
        } else {
            width = 18 * Math.min(stacks.size(), maxWidth);
            height = 18 * Math.min((stacks.size() + maxWidth - 1) / maxWidth, maxHeight) + 2;
        }
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void render(MatrixStack matrices, int x, int y, float delta) {
        for (int i = 0; i < Math.min(stacks.size(), maxWidth * maxHeight); i++) {
            MeganeUtils.drawStack(stacks.get(i), x + (18 * (i % maxWidth)) + 1, y + (18 * (i / maxWidth)));
        }
    }

}
