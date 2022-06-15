package lol.bai.megane.module.reborncore.provider;

import lol.bai.megane.api.provider.FluidProvider;
import net.minecraft.fluid.Fluid;
import org.jetbrains.annotations.Nullable;
import reborncore.common.blockentity.MachineBaseBlockEntity;
import reborncore.common.util.Tank;

public class MachineBaseFluidProvider extends FluidProvider<MachineBaseBlockEntity> {

    private Tank tank;

    @Override
    protected void init() {
        this.tank = getObject().getTank();
    }

    @Override
    public boolean hasFluids() {
        return tank != null;
    }

    @Override
    public int getSlotCount() {
        return 1;
    }

    @Override
    public @Nullable Fluid getFluid(int slot) {
        return tank.getFluid();
    }

    @Override
    public double getStored(int slot) {
        return droplets(tank.getAmount());
    }

    @Override
    public double getMax(int slot) {
        return droplets(tank.getCapacity());
    }

}
