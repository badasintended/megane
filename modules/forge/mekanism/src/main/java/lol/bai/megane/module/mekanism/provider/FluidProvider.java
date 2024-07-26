package lol.bai.megane.module.mekanism.provider;

import lol.bai.megane.module.mekanism.mixin.AccessLookingAtUtils;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.FluidData;
import mcp.mobius.waila.api.forge.ForgeFluidData;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.math.FloatingLong;
import mekanism.common.integration.lookingat.LookingAtHelper;
import mekanism.common.util.CapabilityUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class FluidProvider  implements IDataProvider<BlockEntity>, LookingAtHelper {

    private FluidData.PlatformDependant<FluidStack> fluidData;

    @Override
    public void appendData(IDataWriter data, IServerAccessor<BlockEntity> accessor, IPluginConfig config) {
        data.add(FluidData.class, res -> CapabilityUtils
            .getCapability(accessor.getTarget(), ForgeCapabilities.FLUID_HANDLER, null)
            .ifPresent(handler -> addFluids(res, handler)));
    }

    public void addFluids(IDataWriter.Result<FluidData> res, IFluidHandler handler) {
        fluidData = ForgeFluidData.of();
        AccessLookingAtUtils.megane_displayFluid(this, handler);
        res.add(fluidData);
        fluidData = null;
    }

    @Override
    public void addFluidElement(FluidStack stored, int capacity) {
        fluidData.add(stored, capacity);
    }

    @Override
    public void addText(Component text) {
    }

    @Override
    public void addEnergyElement(FloatingLong energy, FloatingLong maxEnergy) {
    }

    @Override
    public void addChemicalElement(ChemicalStack<?> stored, long capacity) {
    }

}
