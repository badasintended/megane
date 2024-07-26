package lol.bai.megane.module.mekanism.provider;

import lol.bai.megane.module.mekanism.mixin.AccessLookingAtUtils;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.EnergyData;
import mcp.mobius.waila.api.data.FluidData;
import mcp.mobius.waila.api.data.ItemData;
import net.minecraft.world.level.block.entity.BlockEntity;

public class MultiblockProvider implements IDataProvider<BlockEntity> {

    private final FluidProvider fluidProvider;

    public MultiblockProvider(FluidProvider fluidProvider) {
        this.fluidProvider = fluidProvider;
    }

    @Override
    public void appendData(IDataWriter data, IServerAccessor<BlockEntity> accessor, IPluginConfig config) {
        var multiblock = AccessLookingAtUtils.megane_getMultiblock(accessor.getTarget());
        if (multiblock == null || !multiblock.isFormed()) return;

        data.add(EnergyData.class, res ->
            StrictEnergyProvider.addEnergy(res, multiblock));

        data.add(FluidData.class, res ->
            fluidProvider.addFluids(res, multiblock));

        data.add(ItemData.class, res ->
            res.add(ItemData.of(config).getter(multiblock::getStackInSlot, multiblock.getSlots())));

        data.add(ChemicalProvider.Data.class, res ->
            ChemicalProvider.addChemicals(res, accessor.getTarget(), multiblock));
    }

}
