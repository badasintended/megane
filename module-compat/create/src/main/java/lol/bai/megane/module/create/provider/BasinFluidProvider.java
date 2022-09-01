package lol.bai.megane.module.create.provider;

import com.simibubi.create.content.contraptions.processing.BasinTileEntity;
import io.github.fabricators_of_create.porting_lib.transfer.fluid.FluidTank;
import io.github.fabricators_of_create.porting_lib.util.FluidStack;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;

@SuppressWarnings("UnstableApiUsage")
public class BasinFluidProvider extends FluidStackProvider<BasinTileEntity> {

    @Override
    public int getSlotCount() {
        return 2;
    }

    @Override
    FluidStack getFluidStack(int slot) {
        FluidTank tank = slot == 0 ? getObject().getTanks().getFirst().getPrimaryHandler() : getObject().getTanks().getSecond().getPrimaryHandler();
        return tank.getFluid();
    }

    @Override
    public double getMax(int slot) {
        return droplets(FluidConstants.BUCKET);
    }

}
