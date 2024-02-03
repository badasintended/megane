package lol.bai.megane.module.ie.provider;

import java.util.ArrayList;
import java.util.List;

import blusunrize.immersiveengineering.api.multiblocks.blocks.logic.IMultiblockBE;
import blusunrize.immersiveengineering.common.blocks.multiblocks.logic.AssemblerLogic;
import mcp.mobius.waila.api.IBlockAccessor;
import mcp.mobius.waila.api.IBlockComponentProvider;
import mcp.mobius.waila.api.IData;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.ITooltip;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

import static lol.bai.megane.module.ie.MeganeImmersiveEngineering.CONFIG_SHOW_ASSEMBLER_RECIPES;

public class AssemblerProvider implements IBlockComponentProvider, IDataProvider<IMultiblockBE<AssemblerLogic.State>> {

    public static final ResourceLocation DATA = new ResourceLocation("megane:ie.assembler");

    @Override
    public void appendData(IDataWriter data, IServerAccessor<IMultiblockBE<AssemblerLogic.State>> accessor, IPluginConfig config) {
        if (!config.getBoolean(CONFIG_SHOW_ASSEMBLER_RECIPES)) return;

        var state = accessor.getTarget().getHelper().getState();
        if (state == null) return;

        List<ResourceLocation> recipes = null;
        for (var pattern : state.patterns) {
            if (pattern.recipe == null) continue;
            if (recipes == null) recipes = new ArrayList<>();
            recipes.add(pattern.recipe.getId());
        }

        if (recipes != null) data.addImmediate(new Data(recipes));
    }

    @Override
    public void appendBody(ITooltip tooltip, IBlockAccessor accessor, IPluginConfig config) {
        if (!config.getBoolean(CONFIG_SHOW_ASSEMBLER_RECIPES)) return;

        var data = accessor.getData().get(Data.class);
        if (data == null) return;
        if (data.recipes.isEmpty()) return;

        MutableComponent recipeText = null;
        for (var recipeId : data.recipes) {
            var recipe = accessor.getWorld().getRecipeManager().byKey(recipeId).orElse(null);
            if (recipe == null) continue;

            var result = recipe.getResultItem(accessor.getWorld().registryAccess());
            if (result.isEmpty()) continue;

            var text = result.getHoverName();
            if (recipeText == null) recipeText = text.copy();
            else recipeText.append(", ").append(text);
        }

        tooltip.addLine(recipeText);
    }

    public record Data(
        List<ResourceLocation> recipes
    ) implements IData {

        public Data(FriendlyByteBuf buf) {
            this(buf.readList(FriendlyByteBuf::readResourceLocation));
        }

        @Override
        public void write(FriendlyByteBuf buf) {
            buf.writeCollection(recipes, FriendlyByteBuf::writeResourceLocation);
        }

    }

}
