package lol.bai.megane.module.ie;

import blusunrize.immersiveengineering.api.multiblocks.blocks.logic.IMultiblockBE;
import blusunrize.immersiveengineering.common.blocks.metal.CapacitorCreativeBlockEntity;
import blusunrize.immersiveengineering.common.blocks.metal.DynamoBlockEntity;
import blusunrize.immersiveengineering.common.blocks.metal.ThermoelectricGenBlockEntity;
import blusunrize.immersiveengineering.common.blocks.multiblocks.logic.AdvBlastFurnaceLogic;
import blusunrize.immersiveengineering.common.blocks.multiblocks.logic.AlloySmelterLogic;
import blusunrize.immersiveengineering.common.blocks.multiblocks.logic.AssemblerLogic;
import blusunrize.immersiveengineering.common.blocks.multiblocks.logic.BlastFurnaceLogic;
import blusunrize.immersiveengineering.common.blocks.multiblocks.logic.CokeOvenLogic;
import blusunrize.immersiveengineering.common.blocks.multiblocks.logic.DieselGeneratorLogic;
import blusunrize.immersiveengineering.common.blocks.multiblocks.logic.MetalPressLogic;
import blusunrize.immersiveengineering.common.blocks.multiblocks.logic.SheetmetalTankLogic;
import blusunrize.immersiveengineering.common.blocks.multiblocks.logic.SiloLogic;
import blusunrize.immersiveengineering.common.blocks.multiblocks.process.ProcessContext;
import blusunrize.immersiveengineering.common.util.inventory.IIEInventory;
import lol.bai.megane.module.ie.provider.AssemblerProvider;
import lol.bai.megane.module.ie.provider.CokeOvenProvider;
import lol.bai.megane.module.ie.provider.FurnaceProvider;
import lol.bai.megane.module.ie.provider.IEInventoryProvider;
import lol.bai.megane.module.ie.provider.MetalPressProvider;
import lol.bai.megane.module.ie.provider.MultiblockProvider;
import lol.bai.megane.module.ie.provider.ProcessProvider;
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

        var multiblockProvider = new MultiblockProvider();
        registrar.addComponent(multiblockProvider, TooltipPosition.BODY, IMultiblockBE.class);
        registrar.addBlockData(multiblockProvider, IMultiblockBE.class, 100);

        registrar.addBlockData(EnergyData.newInfiniteProvider(), CapacitorCreativeBlockEntity.class, 900);
        registrar.addBlockData(new BlockingDataProvider<>(EnergyData.class), DynamoBlockEntity.class, 900);
        registrar.addBlockData(new BlockingDataProvider<>(EnergyData.class), ThermoelectricGenBlockEntity.class, 900);
        MultiblockProvider.addData(new BlockingDataProvider<>(EnergyData.class), DieselGeneratorLogic.State.class);

        registrar.addBlockData(new IEInventoryProvider(), IIEInventory.class);

        MultiblockProvider.addData(new CokeOvenProvider(), CokeOvenLogic.State.class);
        MultiblockProvider.addData(new FurnaceProvider<>(BlastFurnaceLogic.State::getStateView), BlastFurnaceLogic.State.class);
        MultiblockProvider.addData(new FurnaceProvider<>(AdvBlastFurnaceLogic.State::getStateView), AdvBlastFurnaceLogic.State.class);
        MultiblockProvider.addData(new FurnaceProvider<>(AlloySmelterLogic.State::getStateView), AlloySmelterLogic.State.class);
        MultiblockProvider.addData(new SiloProvider(), SiloLogic.State.class);
        MultiblockProvider.addData(new SheetmetalTankProvider(), SheetmetalTankLogic.State.class);
        MultiblockProvider.addData(new ProcessProvider<>(), ProcessContext.class);

        registrar.addFeatureConfig(CONFIG_SHOW_METAL_PRESS_MOLD, true);
        MultiblockProvider.addBody(new MetalPressProvider(), MetalPressLogic.State.class);

        var assemblerProvider = new AssemblerProvider();
        registrar.addFeatureConfig(CONFIG_SHOW_ASSEMBLER_RECIPES, false);
        registrar.addDataType(AssemblerProvider.DATA, AssemblerProvider.Data.class, AssemblerProvider.Data::new);
        MultiblockProvider.addData(assemblerProvider, AssemblerLogic.State.class);
        MultiblockProvider.addBody(assemblerProvider, AssemblerLogic.State.class);
    }

}
