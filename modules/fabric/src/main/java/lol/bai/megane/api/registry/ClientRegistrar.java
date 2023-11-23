package lol.bai.megane.api.registry;

import lol.bai.megane.api.provider.EnergyInfoProvider;
import lol.bai.megane.api.provider.FluidInfoProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.material.Fluid;

/**
 * @deprecated use WTHIT API
 */
@Deprecated
public interface ClientRegistrar {

    int DEFAULT_PRIORITY = 1000;

    /**
     * Registers static energy info for specified namespace.
     *
     * @param namespace the namespace of the block entity type
     * @param color     the energy bar color
     * @param unit      the energy unit
     * @param name      the energy name
     */
    void addEnergyInfo(String namespace, int color, String unit, Component name);

    /**
     * Registers static energy info for specified namespace.
     *
     * @param namespace the namespace of the block entity type
     * @param color     the energy bar color
     * @param unit      the energy unit
     */
    default void addEnergyInfo(String namespace, int color, String unit) {
        addEnergyInfo(namespace, color, unit, Component.translatable("megane.energy"));
    }

    /**
     * Registers dynamic energy info for specified block entity class.
     *
     * @param clazz    the class of block entity
     * @param provider the provider instance
     * @param priority the priority of the provider
     */
    <T> void addEnergyInfo(Class<T> clazz, EnergyInfoProvider<T> provider, int priority);

    /**
     * Registers dynamic energy info for specified block entity class.
     *
     * @param clazz    the class of block entity
     * @param provider the provider instance
     */
    default <T> void addEnergyInfo(Class<T> clazz, EnergyInfoProvider<T> provider) {
        addEnergyInfo(clazz, provider, DEFAULT_PRIORITY);
    }

    /**
     * Registers static fluid info for specified fluid.
     *
     * @param fluid the fluid
     * @param color the fluid bar color
     * @param name  the fluid name
     */
    void addFluidInfo(Fluid fluid, int color, Component name);

    /**
     * Registers static fluid info for specified fluid.
     *
     * @param fluid    the fluid
     * @param provider the provider instance
     */
    <T extends Fluid> void addFluidInfo(T fluid, FluidInfoProvider<T> provider);

    /**
     * Registers dynamic fluid info for specified fluid class.
     *
     * @param clazz    the class of the fluid
     * @param provider the provider instance
     * @param priority the priority of the provider
     */
    <T> void addFluidInfo(Class<T> clazz, FluidInfoProvider<T> provider, int priority);

    /**
     * Registers dynamic fluid info for specified fluid class.
     *
     * @param clazz    the class of the fluid
     * @param provider the provider instance
     */
    default <T> void addFluidInfo(Class<T> clazz, FluidInfoProvider<T> provider) {
        addFluidInfo(clazz, provider, DEFAULT_PRIORITY);
    }

}
