package lol.bai.megane.module.create.provider;

import com.simibubi.create.content.contraptions.fluids.tank.CreativeFluidTankTileEntity;

public class CreativeFluidTankFluidProvider extends FluidTankFluidProvider<CreativeFluidTankTileEntity> {

    @Override
    public double getStored(int slot) {
        return -1;
    }

    @Override
    public double getMax(int slot) {
        return -1;
    }

}
