package lol.bai.megane.mixin.mekanism;

import java.util.List;
import java.util.function.Function;

import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalHandler;
import mekanism.api.chemical.IChemicalTank;
import mekanism.api.chemical.merged.MergedChemicalTank;
import mekanism.api.text.ILangEntry;
import mekanism.common.capabilities.merged.MergedTank;
import mekanism.common.integration.lookingat.LookingAtHelper;
import mekanism.common.integration.lookingat.LookingAtUtils;
import mekanism.common.lib.multiblock.MultiblockData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LookingAtUtils.class)
public interface AccessLookingAtUtils {

    @Invoker("getMultiblock")
    static @Nullable MultiblockData megane_getMultiblock(BlockEntity tile) {
        throw new AssertionError("mixin");
    }

    @Invoker("displayFluid")
    static void megane_displayFluid(LookingAtHelper info, IFluidHandler fluidHandler) {

    }

    @Invoker("addInfo")
    static <
        CHEMICAL extends Chemical<CHEMICAL>,
        STACK extends ChemicalStack<CHEMICAL>,
        TANK extends IChemicalTank<CHEMICAL, STACK>,
        HANDLER extends IChemicalHandler<CHEMICAL, STACK>
        >
    void megane_addInfo(
        BlockEntity tile,
        @Nullable MultiblockData structure,
        Capability<HANDLER> capability,
        Function<MultiblockData, List<TANK>> multiBlockToTanks,
        LookingAtHelper info,
        ILangEntry langEntry,
        MergedChemicalTank.Current matchingCurrent,
        MergedTank.CurrentType matchingCurrentType) {
    }

}
