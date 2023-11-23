package lol.bai.megane.module.extragenerators.provider;

import java.util.function.Function;
import java.util.function.ToIntFunction;

import io.github.lucaargolo.extragenerators.common.blockentity.AbstractGeneratorBlockEntity;
import io.github.lucaargolo.extragenerators.utils.FluidGeneratorFuel;
import io.github.lucaargolo.extragenerators.utils.GeneratorFuel;
import io.github.lucaargolo.extragenerators.utils.SimpleSidedInventory;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.EnergyData;
import mcp.mobius.waila.api.data.FluidData;
import mcp.mobius.waila.api.data.ItemData;
import mcp.mobius.waila.api.data.ProgressData;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("UnstableApiUsage")
public class GeneratorProvider<T extends AbstractGeneratorBlockEntity<T>, F> implements IDataProvider<T> {

    private final Function<T, SimpleSidedInventory> itemGetter;

    private final Function<T, @Nullable F> fuelGetter;
    private final ToIntFunction<F> currentBurnTimeGetter;
    private final ToIntFunction<F> burnTimeGetter;
    private final int[] inputSlots;

    private final @Nullable Function<T, SingleVariantStorage<FluidVariant>> fluidGetter;

    private GeneratorProvider(Function<T, SimpleSidedInventory> itemGetter, Function<T, @Nullable F> fuelGetter, ToIntFunction<F> currentBurnTimeGetter, ToIntFunction<F> burnTimeGetter, int[] inputSlots, @Nullable Function<T, SingleVariantStorage<FluidVariant>> fluidGetter) {
        this.itemGetter = itemGetter;
        this.fuelGetter = fuelGetter;
        this.currentBurnTimeGetter = currentBurnTimeGetter;
        this.burnTimeGetter = burnTimeGetter;
        this.inputSlots = inputSlots;
        this.fluidGetter = fluidGetter;
    }

    public static <T extends AbstractGeneratorBlockEntity<T>> Builder<T, GeneratorFuel> builder(Class<T> tClass) {
        return new Builder<>(GeneratorFuel::getCurrentBurnTime, GeneratorFuel::getBurnTime);
    }

    public static <T extends AbstractGeneratorBlockEntity<T>> Builder<T, FluidGeneratorFuel> builderWithFluid(Class<T> tClass) {
        return new Builder<>(FluidGeneratorFuel::getCurrentBurnTime, FluidGeneratorFuel::getBurnTime);
    }

    @Override
    public void appendData(IDataWriter data, IServerAccessor<T> accessor, IPluginConfig config) {
        data.add(EnergyData.class, res -> {
            var storage = accessor.getTarget().getEnergyStorage();
            res.add(EnergyData.of(storage.getAmount(), storage.getCapacity()));
        });

        data.add(ItemData.class, res -> {
            var inventory = itemGetter.apply(accessor.getTarget());
            res.add(ItemData.of(config).vanilla(inventory));
        });

        if (fluidGetter != null) data.add(FluidData.class, res -> {
            var storage = fluidGetter.apply(accessor.getTarget());
            var resource = storage.getResource();

            res.add(FluidData.of(FluidData.Unit.DROPLETS, 1)
                .add(resource.getFluid(), resource.getNbt(), storage.getAmount(), storage.getCapacity()));
        });

        data.add(ProgressData.class, res -> {
            var fuel = fuelGetter.apply(accessor.getTarget());
            if (fuel == null) return;

            var currentBurnTime = currentBurnTimeGetter.applyAsInt(fuel);
            var burnTime = burnTimeGetter.applyAsInt(fuel);

            res.add(ProgressData.ratio(1f - (float) currentBurnTime / burnTime)
                .itemGetter(itemGetter.apply(accessor.getTarget())::getItem)
                .input(inputSlots));
        });
    }


    public static class Builder<T extends AbstractGeneratorBlockEntity<T>, F> {

        private final ToIntFunction<F> currentBurnTimeGetter;
        private final ToIntFunction<F> burnTimeGetter;

        private Function<T, SimpleSidedInventory> itemGetter;
        private Function<T, @Nullable F> fuelGetter;
        private int[] inputSlots;
        private @Nullable Function<T, SingleVariantStorage<FluidVariant>> fluidGetter = null;

        public Builder(ToIntFunction<F> currentBurnTimeGetter, ToIntFunction<F> burnTimeGetter) {
            this.currentBurnTimeGetter = currentBurnTimeGetter;
            this.burnTimeGetter = burnTimeGetter;
        }

        public Builder<T, F> item(Function<T, SimpleSidedInventory> itemGetter) {
            this.itemGetter = itemGetter;
            return this;
        }

        public Builder<T, F> fluid(@Nullable Function<T, SingleVariantStorage<FluidVariant>> fluidGetter) {
            this.fluidGetter = fluidGetter;
            return this;
        }

        public Builder<T, F> fuel(Function<T, @Nullable F> fuelGetter, int... inputSlots) {
            this.fuelGetter = fuelGetter;
            this.inputSlots = inputSlots;
            return this;
        }

        public GeneratorProvider<T, F> build() {
            return new GeneratorProvider<>(itemGetter, fuelGetter, currentBurnTimeGetter, burnTimeGetter, inputSlots, fluidGetter);
        }

    }

}
