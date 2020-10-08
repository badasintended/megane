package badasintended.megane.runtime.renderer;

import mcp.mobius.waila.api.ICommonAccessor;
import mcp.mobius.waila.api.ITooltipRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.collection.DefaultedList;

import java.awt.*;
import java.util.List;
import java.util.*;

import static badasintended.megane.runtime.util.RuntimeUtils.drawStack;
import static badasintended.megane.util.MeganeUtils.key;

public class InventoryRenderer implements ITooltipRenderer {

    private DefaultedList<ItemStack> stacks = DefaultedList.ofSize(0, ItemStack.EMPTY);
    private final Set<Item> items = new LinkedHashSet<>();

    private int w;
    private int h;

    @Override
    public Dimension getSize(CompoundTag data, ICommonAccessor accessor) {
        w = data.getInt(key("maxWidth"));
        h = data.getInt(key("maxHeight"));
        int size = data.getInt(key("invSize"));

        stacks = DefaultedList.ofSize(size, ItemStack.EMPTY);
        Inventories.fromTag(data.getCompound(key("inventory")), stacks);

        items.clear();
        stacks.forEach(stack -> {
            if (!stack.isEmpty()) items.add(stack.getItem());
        });

        if (items.size() == 0) return new Dimension();
        return new Dimension(18 * Math.min(items.size(), w), 18 * Math.min(items.size() / w + 1, h) + 2);
    }

    @Override
    public void draw(MatrixStack matrices, CompoundTag data, ICommonAccessor accessor, int x, int y) {
        List<ItemStack> combinedStacks = new ArrayList<>();
        items.forEach(item -> {
            int count = stacks.stream().filter(stack -> stack.getItem() == item).mapToInt(ItemStack::getCount).sum();
            combinedStacks.add(new ItemStack(item, count));
        });

        combinedStacks.sort(Comparator.comparing(ItemStack::getCount, Comparator.reverseOrder()));

        for (int i = 0; i < Math.min(combinedStacks.size(), w * h); i++) {
            drawStack(combinedStacks.get(i), x + (18 * (i % w)) + 1, y + (18 * (i / w)));
        }
    }

}
