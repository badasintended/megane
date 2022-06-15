package lol.bai.megane.api.registry;

import lol.bai.megane.api.provider.CauldronProvider;
import lol.bai.megane.api.provider.EnergyProvider;
import lol.bai.megane.api.provider.FluidProvider;
import lol.bai.megane.api.provider.ItemProvider;
import lol.bai.megane.api.provider.ProgressProvider;
import net.minecraft.block.Block;

public interface CommonRegistrar {

    int DEFAULT_PRIORITY = 1000;

    <T> void addEnergy(int priority, Class<T> clazz, EnergyProvider<T> provider);

    default <T> void addEnergy(Class<T> clazz, EnergyProvider<T> provider) {
        addEnergy(DEFAULT_PRIORITY, clazz, provider);
    }

    <T> void addFluid(int priority, Class<T> clazz, FluidProvider<T> provider);

    default <T> void addFluid(Class<T> clazz, FluidProvider<T> provider) {
        addFluid(DEFAULT_PRIORITY, clazz, provider);
    }

    void addCauldron(Block block, CauldronProvider provider);

    <T> void addCauldron(int priority, Class<T> clazz, CauldronProvider provider);

    default <T> void addCauldron(Class<T> clazz, CauldronProvider provider) {
        addCauldron(DEFAULT_PRIORITY, clazz, provider);
    }

    <T> void addItem(int priority, Class<T> clazz, ItemProvider<T> provider);

    default <T> void addItem(Class<T> clazz, ItemProvider<T> provider) {
        addItem(DEFAULT_PRIORITY, clazz, provider);
    }

    <T> void addProgress(int priority, Class<T> clazz, ProgressProvider<T> provider);

    default <T> void addProgress(Class<T> clazz, ProgressProvider<T> provider) {
        addProgress(DEFAULT_PRIORITY, clazz, provider);
    }

}
