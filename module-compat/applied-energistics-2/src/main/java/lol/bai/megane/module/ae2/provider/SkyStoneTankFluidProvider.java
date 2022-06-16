package lol.bai.megane.module.ae2.provider;

import appeng.blockentity.storage.SkyStoneTankBlockEntity;
import lol.bai.megane.api.provider.FluidProvider;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("UnstableApiUsage")
public class SkyStoneTankFluidProvider extends FluidProvider<SkyStoneTankBlockEntity> {

    private SingleVariantStorage<FluidVariant> storage;

    @Override
    protected void init() {
        storage = getObject().getStorage();
    }

    @Override
    public int getSlotCount() {
        return 1;
    }

    @Override
    public @Nullable Fluid getFluid(int slot) {
        return storage.variant.getFluid();
    }

    @Override
    public @Nullable NbtCompound getNbt(int slot) {
        return storage.variant.getNbt();
    }

    @Override
    public double getStored(int slot) {
        return droplets(storage.amount);
    }

    @Override
    public double getMax(int slot) {
        return 16 * 1000;
    }

}
