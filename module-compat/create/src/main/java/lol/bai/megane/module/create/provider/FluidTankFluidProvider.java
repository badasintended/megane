package lol.bai.megane.module.create.provider;

import com.simibubi.create.content.fluids.tank.FluidTankBlockEntity;
import io.github.fabricators_of_create.porting_lib.transfer.fluid.FluidTank;
import io.github.fabricators_of_create.porting_lib.util.FluidStack;

public class FluidTankFluidProvider<T extends FluidTankBlockEntity> extends FluidStackProvider<T> {

    FluidTank tank;

    @Override
    protected void init() {
        tank = getObject().getControllerBE().getTankInventory();
    }

    @Override
    public int getSlotCount() {
        return 1;
    }

    @Override
    FluidStack getFluidStack(int slot) {
        return tank.getFluid();
    }

    @Override
    public double getMax(int slot) {
        return droplets(tank.getCapacity());
    }

}
