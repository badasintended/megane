package lol.bai.megane.module.create.provider;

import com.simibubi.create.content.fluids.tank.CreativeFluidTankBlockEntity;

public class CreativeFluidTankFluidProvider extends FluidTankFluidProvider<CreativeFluidTankBlockEntity> {

    @Override
    public double getStored(int slot) {
        return -1;
    }

    @Override
    public double getMax(int slot) {
        return -1;
    }

}
