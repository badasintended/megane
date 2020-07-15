package badasintended.megane.provider;

import badasintended.megane.Megane;
import badasintended.megane.mixin.BlockAccessor;
import badasintended.megane.mixin.BlockSettingsAccessor;
import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterials;
import net.minecraft.tag.Tag;
import net.minecraft.text.BaseText;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static badasintended.megane.TextUtil.format;
import static badasintended.megane.TextUtil.translate;

public class EffectiveToolProvider implements IComponentProvider {

    public static final EffectiveToolProvider INSTANCE = new EffectiveToolProvider();

    public static final BaseText UNKNOWN = new LiteralText("unknown");

    public static final Map<Tag<Item>, ItemStack> TOOLS = new HashMap<>();
    public static final Map<Tag<Item>, MutableText> TOOL_NAMES = new HashMap<>();

    static {
        TOOLS.put(FabricToolTags.AXES, new ItemStack(Items.WOODEN_AXE));
        TOOLS.put(FabricToolTags.PICKAXES, new ItemStack(Items.WOODEN_PICKAXE));
        TOOLS.put(FabricToolTags.SHOVELS, new ItemStack(Items.WOODEN_SHOVEL));
        TOOLS.put(FabricToolTags.HOES, new ItemStack(Items.WOODEN_HOE));

        TOOL_NAMES.put(FabricToolTags.AXES, translate("tools.axe"));
        TOOL_NAMES.put(FabricToolTags.PICKAXES, translate("tools.pickaxe"));
        TOOL_NAMES.put(FabricToolTags.SHOVELS, translate("tools.shovel"));
        TOOL_NAMES.put(FabricToolTags.HOES, translate("tools.hoe"));
    }

    @Override
    public void appendBody(List<Text> tooltip, IDataAccessor accessor, IPluginConfig config) {
        if (config.get(Megane.Config.EFFECTIVE_TOOL)) {
            Block block = accessor.getBlock();
            BlockState state = accessor.getBlockState();
            AbstractBlock.Settings blockSettings = ((BlockAccessor) accessor.getBlock()).getSettings();
            BlockSettingsAccessor blockSettingsAccessor = (BlockSettingsAccessor) blockSettings;

            Tag<Item> effectiveTool = null;
            MutableText toolName = UNKNOWN;

            if (Items.SHEARS.isEffectiveOn(state) || block instanceof LeavesBlock) {
                toolName = translate("tools.shears");
                effectiveTool = FabricToolTags.SHEARS;
            } else if (blockSettingsAccessor.getHardness() == 0.0F) {
                toolName = translate("tools.none");
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

            if (effectiveTool != null) {
                if (effectiveTool.contains(accessor.getPlayer().getMainHandStack().getItem())) color = Formatting.GREEN;
                else color = Formatting.RED;
            } else color = Formatting.GRAY;

            if (!blockSettingsAccessor.getToolRequired()) color = Formatting.GREEN;

            tooltip.add(translate("tool").append(": ").append(format(toolName, color)));
        }
    }



}
