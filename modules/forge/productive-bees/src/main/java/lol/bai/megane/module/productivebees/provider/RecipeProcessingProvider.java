package lol.bai.megane.module.productivebees.provider;

import cy.jdkdigital.productivebees.common.block.entity.IRecipeProcessingBlockEntity;
import it.unimi.dsi.fastutil.ints.IntList;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.ProgressData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

public class RecipeProcessingProvider<T extends BlockEntity & IRecipeProcessingBlockEntity> implements IDataProvider<T> {

    private final int[] input, output;

    public RecipeProcessingProvider(IntList input, IntList output) {
        this.input = input.toIntArray();
        this.output = output.toIntArray();
    }

    @Override
    public void appendData(IDataWriter data, IServerAccessor<T> accessor, IPluginConfig config) {
        data.add(ProgressData.class, res -> {
            var target = accessor.getTarget();
            target.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(inv -> res.add(ProgressData
                .ratio((float) target.getRecipeProgress() / target.getProcessingTime())
                .itemGetter(inv::getStackInSlot)
                .input(input)
                .output(output)));
        });
    }

}
