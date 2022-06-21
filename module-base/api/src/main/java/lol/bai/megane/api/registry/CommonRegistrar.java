package lol.bai.megane.api.registry;

import lol.bai.megane.api.provider.CauldronProvider;
import lol.bai.megane.api.provider.EnergyProvider;
import lol.bai.megane.api.provider.FluidProvider;
import lol.bai.megane.api.provider.ItemProvider;
import lol.bai.megane.api.provider.ProgressProvider;
import net.minecraft.block.Block;

public interface CommonRegistrar {

    int DEFAULT_PRIORITY = 1000;

    /**
     * Registers energy provider for specified block entity class
     *
     * @param priority the priority of the provider
     * @param clazz    the block entity class
     * @param provider the provider instance
     */
    <T> void addEnergy(int priority, Class<T> clazz, EnergyProvider<T> provider);

    /**
     * Registers energy provider for specified block entity class
     *
     * @param clazz    the block entity class
     * @param provider the provider instance
     */
    default <T> void addEnergy(Class<T> clazz, EnergyProvider<T> provider) {
        addEnergy(DEFAULT_PRIORITY, clazz, provider);
    }

    /**
     * Registers fluid provider for specified block entity class
     *
     * @param priority the priority of the provider
     * @param clazz    the block entity class
     * @param provider the provider instance
     */
    <T> void addFluid(int priority, Class<T> clazz, FluidProvider<T> provider);

    /**
     * Registers fluid provider for specified block entity class
     *
     * @param clazz    the block entity class
     * @param provider the provider instance
     */
    default <T> void addFluid(Class<T> clazz, FluidProvider<T> provider) {
        addFluid(DEFAULT_PRIORITY, clazz, provider);
    }

    /**
     * Registers cauldron fluid provider for specified block
     *
     * @param block    the cauldron block
     * @param provider the provider instance
     */
    void addCauldron(Block block, CauldronProvider provider);

    /**
     * Registers cauldron fluid provider for specified block class
     *
     * @param priority the priority of the provider
     * @param clazz    the block class
     * @param provider the provider instance
     */
    <T> void addCauldron(int priority, Class<T> clazz, CauldronProvider provider);

    /**
     * Registers cauldron fluid provider for specified block class
     *
     * @param clazz    the block class
     * @param provider the provider instance
     */
    default <T> void addCauldron(Class<T> clazz, CauldronProvider provider) {
        addCauldron(DEFAULT_PRIORITY, clazz, provider);
    }

    /**
     * Registers item provider for specified block or entity class
     *
     * @param priority the priority of the provider
     * @param clazz    the block or entity class
     * @param provider the provider instance
     */
    <T> void addItem(int priority, Class<T> clazz, ItemProvider<T> provider);

    /**
     * Registers item provider for specified block or entity class
     *
     * @param clazz    the block or entity class
     * @param provider the provider instance
     */
    default <T> void addItem(Class<T> clazz, ItemProvider<T> provider) {
        addItem(DEFAULT_PRIORITY, clazz, provider);
    }

    /**
     * Registers progress provider for specified block class
     *
     * @param priority the priority of the provider
     * @param clazz    the block class
     * @param provider the provider instance
     */
    <T> void addProgress(int priority, Class<T> clazz, ProgressProvider<T> provider);

    /**
     * Registers progress provider for specified block class
     *
     * @param clazz    the block class
     * @param provider the provider instance
     */
    default <T> void addProgress(Class<T> clazz, ProgressProvider<T> provider) {
        addProgress(DEFAULT_PRIORITY, clazz, provider);
    }

}
