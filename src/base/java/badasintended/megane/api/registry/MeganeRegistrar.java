package badasintended.megane.api.registry;

import badasintended.megane.api.provider.CauldronFluidProvider;
import badasintended.megane.api.provider.EnergyProvider;
import badasintended.megane.api.provider.FluidProvider;
import badasintended.megane.api.provider.InventoryProvider;
import badasintended.megane.api.provider.ProgressProvider;
import net.minecraft.block.Block;

public interface MeganeRegistrar {

    int DEFAULT_PRIORITY = 1000;

    MeganeRegistrar cauldron(Block cauldron, CauldronFluidProvider provider);

    <T> MeganeRegistrar energy(int priority, Class<T> clazz, EnergyProvider<T> provider);

    <T> MeganeRegistrar fluid(int priority, Class<T> clazz, FluidProvider<T> provider);

    <T> MeganeRegistrar inventory(int priority, Class<T> clazz, InventoryProvider<T> provider);

    <T> MeganeRegistrar progress(int priority, Class<T> clazz, ProgressProvider<T> provider);

    default <T> MeganeRegistrar energy(Class<T> clazz, EnergyProvider<T> provider) {
        return energy(DEFAULT_PRIORITY, clazz, provider);
    }

    default <T> MeganeRegistrar fluid(Class<T> clazz, FluidProvider<T> provider) {
        return fluid(DEFAULT_PRIORITY, clazz, provider);
    }

    default <T> MeganeRegistrar inventory(Class<T> clazz, InventoryProvider<T> provider) {
        return inventory(DEFAULT_PRIORITY, clazz, provider);
    }

    default <T> MeganeRegistrar progress(Class<T> clazz, ProgressProvider<T> provider) {
        return progress(DEFAULT_PRIORITY, clazz, provider);
    }

}
