package lol.bai.megane.module.extragenerators.provider;

import java.util.function.Function;

import io.github.lucaargolo.extragenerators.common.blockentity.AbstractGeneratorBlockEntity;
import lol.bai.megane.api.provider.FluidProvider;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.fluid.Fluid;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings({"rawtypes", "UnstableApiUsage"})
public class GeneratorFluidProvider<T extends AbstractGeneratorBlockEntity> extends FluidProvider<T> {

    private final Function<T, SingleVariantStorage<FluidVariant>> storageGetter;
    private SingleVariantStorage<FluidVariant> storage;

    public GeneratorFluidProvider(Function<T, SingleVariantStorage<FluidVariant>> storageGetter) {
        this.storageGetter = storageGetter;
    }

    @Override
    protected void init() {
        this.storage = storageGetter.apply(getObject());
    }

    @Override
    public int getSlotCount() {
        return 1;
    }

    @Override
    public @Nullable Fluid getFluid(int slot) {
        return storage.getResource().getFluid();
    }

    @Override
    public double getStored(int slot) {
        return droplets(storage.getAmount());
    }

    @Override
    public double getMax(int slot) {
        return droplets(storage.getCapacity());
    }

}
