package badasintended.megane.tooltip.renderer;

import mcp.mobius.waila.api.ICommonAccessor;
import mcp.mobius.waila.api.ITooltipRenderer;
import mcp.mobius.waila.overlay.DisplayUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.collection.DefaultedList;

import java.awt.*;
import java.util.List;
import java.util.*;

import static badasintended.megane.MeganeUtils.CONFIG;
import static badasintended.megane.MeganeUtils.key;

public class InventoryRenderer implements ITooltipRenderer {

    public static final InventoryRenderer INSTANCE = new InventoryRenderer();

    private static final Dimension ZERO = new Dimension();

    private DefaultedList<ItemStack> stacks = DefaultedList.ofSize(0, ItemStack.EMPTY);
    private Set<Item> items = new LinkedHashSet<>();

    private InventoryRenderer() {
    }

    @Override
    public Dimension getSize(CompoundTag data, ICommonAccessor accessor) {
        int row = CONFIG.get().inventory.getMaxRow();
        int size = data.getInt(key("invSize"));

        stacks = DefaultedList.ofSize(size, ItemStack.EMPTY);
        Inventories.fromTag(data.getCompound(key("inventory")), stacks);

        items = new LinkedHashSet<>();
        stacks.forEach(stack -> {
            if (!stack.isEmpty()) items.add(stack.getItem());
        });

        if (items.size() == 0) return ZERO;
        return new Dimension(18 * Math.min(items.size(), row), 18 * (items.size() / row + 1));
    }

    @Override
    public void draw(MatrixStack matrices, CompoundTag data, ICommonAccessor accessor, int x, int y) {
        int row = CONFIG.get().inventory.getMaxRow();
        List<ItemStack> combinedStacks = new ArrayList<>();
        items.forEach(item -> {
            int count = stacks.stream().filter(stack -> stack.getItem() == item).mapToInt(ItemStack::getCount).sum();
            combinedStacks.add(new ItemStack(item, count));
        });

        combinedStacks.sort(Comparator.comparing(ItemStack::getCount, Comparator.reverseOrder()));

        for (int i = 0; i < combinedStacks.size(); i++) {
            DisplayUtil.renderStack(matrices, x + (18 * (i % row)) + 1, y + (18 * (i / row)) + 1, combinedStacks.get(i));
        }
    }

}
