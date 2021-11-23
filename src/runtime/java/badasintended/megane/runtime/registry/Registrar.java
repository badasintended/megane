package badasintended.megane.runtime.registry;

import badasintended.megane.api.provider.CauldronFluidProvider;
import badasintended.megane.api.provider.EnergyInfoProvider;
import badasintended.megane.api.provider.EnergyProvider;
import badasintended.megane.api.provider.FluidInfoProvider;
import badasintended.megane.api.provider.FluidProvider;
import badasintended.megane.api.provider.InventoryProvider;
import badasintended.megane.api.provider.ProgressProvider;
import badasintended.megane.api.registry.MeganeClientRegistrar;
import badasintended.megane.api.registry.MeganeRegistrar;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.text.Text;

@SuppressWarnings("rawtypes")
public enum Registrar implements MeganeRegistrar, MeganeClientRegistrar {

    INSTANCE;

    public static final Registry<EnergyProvider> ENERGY = new Registry<>();
    public static final Registry<FluidProvider> FLUID = new Registry<>();
    public static final Registry<CauldronFluidProvider> CAULDRON = new Registry<>();
    public static final Registry<InventoryProvider> INVENTORY = new Registry<>();
    public static final Registry<ProgressProvider> PROGRESS = new Registry<>();
    public static final Registry<FluidInfoProvider> FLUID_INFO = new Registry<>();
    public static final Registry<EnergyInfoProvider> ENERGY_INFO = new Registry<>();

    @Override
    public <T> MeganeRegistrar energy(int priority, Class<T> clazz, EnergyProvider<T> provider) {
        ENERGY.add(clazz, provider, priority);
        return this;
    }

    @Override
    public <T> MeganeRegistrar fluid(int priority, Class<T> clazz, FluidProvider<T> provider) {
        FLUID.add(clazz, provider, priority);
        return this;
    }

    @Override
    public MeganeRegistrar cauldron(Block cauldron, CauldronFluidProvider provider) {
        CAULDRON.add(cauldron, provider);
        return this;
    }

    @Override
    public <T> MeganeRegistrar cauldron(int priority, Class<T> clazz, CauldronFluidProvider provider) {
        CAULDRON.add(clazz, provider, priority);
        return this;
    }

    @Override
    public <T> MeganeRegistrar inventory(int priority, Class<T> clazz, InventoryProvider<T> provider) {
        INVENTORY.add(clazz, provider, priority);
        return this;
    }

    @Override
    public <T> MeganeRegistrar progress(int priority, Class<T> clazz, ProgressProvider<T> provider) {
        PROGRESS.add(clazz, provider, priority);
        return this;
    }

    @Override
    public MeganeClientRegistrar fluid(Fluid fluid, int color, Text name) {
        FLUID_INFO.add(fluid, FluidInfoProvider.of(color, name));
        return this;
    }

    @Override
    public <T> MeganeClientRegistrar fluid(Class<T> fluid, FluidInfoProvider<T> provider) {
        FLUID_INFO.add(fluid, provider, DEFAULT_PRIORITY);
        return this;
    }

    @Override
    public MeganeClientRegistrar energy(String namespace, int color, String unit, Text name) {
        ENERGY_INFO.add(namespace, EnergyInfoProvider.of(color, unit, name));
        return this;
    }
}
