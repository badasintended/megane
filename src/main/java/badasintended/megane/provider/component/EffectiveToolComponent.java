package badasintended.megane.provider.component;

import badasintended.megane.Megane;
import badasintended.megane.mixin.BlockAccessor;
import badasintended.megane.mixin.BlockSettingsAccessor;
import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.*;
import net.minecraft.tag.Tag;
import net.minecraft.text.BaseText;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static badasintended.megane.MeganeUtils.format;
import static badasintended.megane.MeganeUtils.tl;

public class EffectiveToolComponent implements IComponentProvider {

    public static final EffectiveToolComponent INSTANCE = new EffectiveToolComponent();

    private static final BaseText UNKNOWN = new LiteralText("unknown");
    private static final BaseText EMPTY = new LiteralText("");
    private static final MutableText ANY = tl("tools.any");

    private static final Map<Tag<Item>, ItemStack> TOOLS = new LinkedHashMap<>();
    private static final Map<Tag<Item>, MutableText> TOOL_NAMES = new HashMap<>();

    private static final Map<Item, MutableText> PICKAXE_TIER = new LinkedHashMap<>();

    static {
        TOOLS.put(FabricToolTags.SHEARS, new ItemStack(Items.SHEARS));
        TOOLS.put(FabricToolTags.AXES, new ItemStack(Items.WOODEN_AXE));
        TOOLS.put(FabricToolTags.PICKAXES, new ItemStack(Items.WOODEN_PICKAXE));
        TOOLS.put(FabricToolTags.SHOVELS, new ItemStack(Items.WOODEN_SHOVEL));
        TOOLS.put(FabricToolTags.HOES, new ItemStack(Items.WOODEN_HOE));

        TOOL_NAMES.put(FabricToolTags.AXES, tl("tools.axe"));
        TOOL_NAMES.put(FabricToolTags.PICKAXES, tl("tools.pickaxe"));
        TOOL_NAMES.put(FabricToolTags.SHOVELS, tl("tools.shovel"));
        TOOL_NAMES.put(FabricToolTags.HOES, tl("tools.hoe"));

        PICKAXE_TIER.put(Items.WOODEN_PICKAXE, tl("tiers.wood"));
        PICKAXE_TIER.put(Items.STONE_PICKAXE, tl("tiers.stone"));
        PICKAXE_TIER.put(Items.IRON_PICKAXE, tl("tiers.iron"));
        PICKAXE_TIER.put(Items.DIAMOND_PICKAXE, tl("tiers.diamond"));
        PICKAXE_TIER.put(Items.NETHERITE_PICKAXE, tl("tiers.netherite"));
    }

    private EffectiveToolComponent() {
    }

    @Override
    public void appendBody(List<Text> tooltip, IDataAccessor accessor, IPluginConfig config) {
        if (config.get(Megane.EFFECTIVE_TOOL)) {
            BlockState state = accessor.getBlockState();
            AbstractBlock.Settings blockSettings = ((BlockAccessor) accessor.getBlock()).getSettings();
            BlockSettingsAccessor blockSettingsAccessor = (BlockSettingsAccessor) blockSettings;

            boolean toolRequired = blockSettingsAccessor.getToolRequired();
            Tag<Item> effectiveTool = null;
            MutableText toolName = UNKNOWN;

            if (blockSettingsAccessor.getHardness() == 0.0F) {
                toolName = ANY;
            } else {
                for (Map.Entry<Tag<Item>, ItemStack> entry : TOOLS.entrySet()) {
                    ItemStack tool = entry.getValue();
                    if (tool.getMiningSpeedMultiplier(state) >= ToolMaterials.WOOD.getMiningSpeedMultiplier()) {
                        effectiveTool = entry.getKey();
                        toolName = TOOL_NAMES.get(effectiveTool);
                        break;
                    }
                }
            }

            Formatting color;
            Item item = accessor.getPlayer().getMainHandStack().getItem();

            MutableText toolTier = EMPTY.copy();

            if (effectiveTool == null) {
                color = Formatting.GRAY;
            } else if (effectiveTool == FabricToolTags.PICKAXES) {
                int tier = 0;
                MutableText tierText = UNKNOWN;
                for (Map.Entry<Item, MutableText> entry : PICKAXE_TIER.entrySet()) {
                    Item pickaxe = entry.getKey();
                    if (pickaxe.isEffectiveOn(state)) {
                        tier = ((PickaxeItem) pickaxe).getMaterial().getMiningLevel();
                        tierText = entry.getValue();
                        break;
                    }
                }
                if (item instanceof PickaxeItem) {
                    color = (((PickaxeItem) item).getMaterial().getMiningLevel() >= tier) ? Formatting.GREEN : Formatting.RED;
                } else color = toolRequired ? Formatting.RED : Formatting.GREEN;
                toolTier.append(" (").append(tl("tier")).append(tierText).append(")").formatted(color);
            } else {
                if (effectiveTool.contains(item)) color = Formatting.GREEN;
                else color = Formatting.RED;
            }

            if (!toolRequired) color = Formatting.GREEN;

            tooltip.add(tl("tool").append(format(toolName, color)).append(toolTier));
        }
    }

}
