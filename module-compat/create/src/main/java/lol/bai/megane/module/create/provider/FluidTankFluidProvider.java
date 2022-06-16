package lol.bai.megane.module.create.provider;

import com.simibubi.create.content.contraptions.fluids.tank.FluidTankTileEntity;
import io.github.fabricators_of_create.porting_lib.transfer.fluid.FluidTank;
import lol.bai.megane.api.provider.FluidProvider;
import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.Nullable;

public class FluidTankFluidProvider<T extends FluidTankTileEntity> extends FluidProvider<T> {

    FluidTank tank;

    @Override
    protected void init() {
        tank = getObject().getControllerTE().getTankInventory();
    }

    @Override
    public int getSlotCount() {
        return 1;
    }

    @Override
    public @Nullable Fluid getFluid(int slot) {
        return tank.getFluid().getFluid();
    }

    @Override
    public @Nullable NbtCompound getNbt(int slot) {
        return tank.getFluid().getTag();
    }

    @Override
    public double getStored(int slot) {
        return droplets(tank.getFluid().getAmount());
    }

    @Override
    public double getMax(int slot) {
        return droplets(tank.getCapacity());
    }

}
