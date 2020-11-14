package badasintended.megane.runtime.renderer;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import mcp.mobius.waila.api.ICommonAccessor;
import mcp.mobius.waila.api.ITooltipRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.registry.Registry;

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
            Item item = Registry.ITEM.get(data.getInt(key("itemId" + i)));
            int count = data.getInt(key("itemCount" + i));
            if (count <= 0) continue;
            CompoundTag tag = data.getCompound(key("itemTag" + i));
            Optional<ItemStack> optional = stacks
                .stream()
                .filter(j -> j.getItem() == item && j.getOrCreateTag().equals(tag))
                .findFirst();
            if (optional.isPresent()) {
                optional.get().increment(count);
            } else {
                ItemStack stack = new ItemStack(item, count);
                stack.setTag(tag);
                stacks.add(stack);
            }
        }

        if (stacks.size() == 0) return new Dimension();
        return new Dimension(18 * Math.min(stacks.size(), w), 18 * Math.min(stacks.size() / w + 1, h) + 2);
    }

    @Override
    public void draw(MatrixStack matrices, CompoundTag data, ICommonAccessor accessor, int x, int y) {
        for (int i = 0; i < Math.min(stacks.size(), w * h); i++) {
            drawStack(stacks.get(i), x + (18 * (i % w)) + 1, y + (18 * (i / w)));
        }
    }

}
