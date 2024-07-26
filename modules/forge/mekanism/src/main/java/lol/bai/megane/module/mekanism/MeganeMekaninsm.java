package lol.bai.megane.module.mekanism;

import lol.bai.megane.module.mekanism.provider.ChemicalProvider;
import lol.bai.megane.module.mekanism.provider.FluidProvider;
import lol.bai.megane.module.mekanism.provider.MultiblockProvider;
import lol.bai.megane.module.mekanism.provider.StrictEnergyProvider;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.TooltipPosition;
import mcp.mobius.waila.api.data.EnergyData;
import mekanism.common.lib.multiblock.IMultiblockBase;
import mekanism.common.tile.base.TileEntityUpdateable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;

public class MeganeMekaninsm implements IWailaPlugin {

    public static final ResourceLocation CONFIG_SHOW_CHEMICALS = id("chemical");

    public static ResourceLocation id(String path) {
        return new ResourceLocation("megane", "mekanism." + path);
    }

    @Override
    public void register(IRegistrar registrar) {
        EnergyData.describe("mekanism").color(0x3CFE9A);

        registrar.addFeatureConfig(CONFIG_SHOW_CHEMICALS, false);
        registrar.addDataType(ChemicalProvider.DATA, ChemicalProvider.Data.class, ChemicalProvider.Data::new);
        registrar.addBlockData(new ChemicalProvider.Blocker(), BlockEntity.class, 0);
        var chemicalProvider = new ChemicalProvider();
        registrar.addBlockData(chemicalProvider, BlockEntity.class, 1500);
        registrar.addComponent(chemicalProvider, TooltipPosition.BODY, BlockEntity.class, 600);

        registrar.addBlockData(new StrictEnergyProvider(), BlockEntity.class, 1500);

        var fluidProvider = new FluidProvider();
        registrar.addBlockData(fluidProvider, TileEntityUpdateable.class);

        registrar.addBlockData(new MultiblockProvider(fluidProvider), IMultiblockBase.class, 900);
    }

}
