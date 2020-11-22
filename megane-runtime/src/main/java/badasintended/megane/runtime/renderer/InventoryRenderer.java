package badasintended.megane.runtime.renderer;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import mcp.mobius.waila.api.ICommonAccessor;
import mcp.mobius.waila.api.ITooltipRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;

import static badasintended.megane.runtime.util.RuntimeUtils.drawStack;
import static badasintended.megane.util.MeganeUtils.key;

public class InventoryRenderer implements ITooltipRenderer {

    private final List<ItemStack> stacks = new ArrayList<>();

    private int w;
    private int h;

    @Override
    public Dimension getSize(CompoundTag data, ICommonAccessor accessor) {
        w = data.getInt(key("maxWidth"));
        h = data.getInt(key("maxHeight"));
        int size = data.getInt(key("invSize"));

        stacks.clear();
        for (int i = 0; i < size; i++) {
            ItemStack stack = ItemStack.fromTag(data.getCompound(key("item" + i)));
            if (stack.isEmpty())
                continue;
            Optional<ItemStack> optional = stacks
                .stream()
                .filter(j -> j.getItem() == stack.getItem() && j.getOrCreateTag().equals(stack.getOrCreateTag()))
                .findFirst();
            if (optional.isPresent()) {
                optional.get().increment(stack.getCount());
            } else {
                stacks.add(stack);
            }
        }

        stacks.sort(Comparator.comparingInt(ItemStack::getCount).reversed());

        if (stacks.size() == 0)
            return new Dimension();
        return new Dimension(18 * Math.min(stacks.size(), w), 18 * Math.min(stacks.size() / w + 1, h) + 2);
    }

    @Override
    public void draw(MatrixStack matrices, CompoundTag data, ICommonAccessor accessor, int x, int y) {
        for (int i = 0; i < Math.min(stacks.size(), w * h); i++) {
            drawStack(stacks.get(i), x + (18 * (i % w)) + 1, y + (18 * (i / w)));
        }
    }

}
