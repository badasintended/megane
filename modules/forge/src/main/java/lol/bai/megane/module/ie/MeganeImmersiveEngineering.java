package lol.bai.megane.module.ie;

import blusunrize.immersiveengineering.common.blocks.metal.AssemblerBlockEntity;
import blusunrize.immersiveengineering.common.blocks.metal.CapacitorCreativeBlockEntity;
import blusunrize.immersiveengineering.common.blocks.metal.DynamoBlockEntity;
import blusunrize.immersiveengineering.common.blocks.metal.MetalPressBlockEntity;
import blusunrize.immersiveengineering.common.blocks.metal.SheetmetalTankBlockEntity;
import blusunrize.immersiveengineering.common.blocks.metal.SiloBlockEntity;
import blusunrize.immersiveengineering.common.blocks.metal.ThermoelectricGenBlockEntity;
import blusunrize.immersiveengineering.common.blocks.stone.CokeOvenBlockEntity;
import blusunrize.immersiveengineering.common.blocks.stone.FurnaceLikeBlockEntity;
import blusunrize.immersiveengineering.common.util.inventory.IIEInventory;
import lol.bai.megane.module.ie.provider.AssemblerProvider;
import lol.bai.megane.module.ie.provider.CokeOvenProvider;
import lol.bai.megane.module.ie.provider.FurnaceLikeProvider;
import lol.bai.megane.module.ie.provider.IEInventoryProvider;
import lol.bai.megane.module.ie.provider.MetalPressProvider;
import lol.bai.megane.module.ie.provider.SheetmetalTankProvider;
import lol.bai.megane.module.ie.provider.SiloProvider;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.TooltipPosition;
import mcp.mobius.waila.api.data.BlockingDataProvider;
import mcp.mobius.waila.api.data.EnergyData;
import net.minecraft.resources.ResourceLocation;

public class MeganeImmersiveEngineering implements IWailaPlugin {

    public static final ResourceLocation CONFIG_SHOW_METAL_PRESS_MOLD = new ResourceLocation("megane:ie.metal_press_mold");
    public static final ResourceLocation CONFIG_SHOW_ASSEMBLER_RECIPES = new ResourceLocation("megane:ie.assembler_recipes");

    @Override
    public void register(IRegistrar registrar) {
        EnergyData.describe("immersiveengineering").unit("IF");
        registrar.addBlockData(EnergyData.newInfiniteProvider(), CapacitorCreativeBlockEntity.class, 900);
        registrar.addBlockData(new BlockingDataProvider<>(EnergyData.class), DynamoBlockEntity.class, 900);
        registrar.addBlockData(new BlockingDataProvider<>(EnergyData.class), ThermoelectricGenBlockEntity.class, 900);

        registrar.addBlockData(new IEInventoryProvider(), IIEInventory.class);
        registrar.addBlockData(new CokeOvenProvider(), CokeOvenBlockEntity.class);
        registrar.addBlockData(new FurnaceLikeProvider(), FurnaceLikeBlockEntity.class);
        registrar.addBlockData(new SiloProvider(), SiloBlockEntity.class);
        registrar.addBlockData(new SheetmetalTankProvider(), SheetmetalTankBlockEntity.class);

        registrar.addFeatureConfig(CONFIG_SHOW_METAL_PRESS_MOLD, true);
        registrar.addComponent(new MetalPressProvider(), TooltipPosition.BODY, MetalPressBlockEntity.class);

        var assemblerProvider = new AssemblerProvider();
        registrar.addFeatureConfig(CONFIG_SHOW_ASSEMBLER_RECIPES, false);
        registrar.addDataType(AssemblerProvider.DATA, AssemblerProvider.Data.class, AssemblerProvider.Data::new);
        registrar.addBlockData(assemblerProvider, AssemblerBlockEntity.class);
        registrar.addComponent(assemblerProvider, TooltipPosition.BODY, AssemblerBlockEntity.class);
    }

}
