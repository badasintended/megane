package lol.bai.megane.runtime.registry;

import lol.bai.megane.api.provider.CauldronProvider;
import lol.bai.megane.api.provider.EnergyInfoProvider;
import lol.bai.megane.api.provider.EnergyProvider;
import lol.bai.megane.api.provider.FluidInfoProvider;
import lol.bai.megane.api.provider.FluidProvider;
import lol.bai.megane.api.provider.ItemProvider;
import lol.bai.megane.api.provider.ProgressProvider;
import lol.bai.megane.api.registry.ClientRegistrar;
import lol.bai.megane.api.registry.CommonRegistrar;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("rawtypes")
public enum Registrar implements CommonRegistrar, ClientRegistrar {

    INSTANCE;

    public static final Registry<EnergyProvider> ENERGY = new Registry<>();
    public static final Registry<FluidProvider> FLUID = new Registry<>();
    public static final Registry<CauldronProvider> CAULDRON = new Registry<>();
    public static final Registry<ItemProvider> INVENTORY = new Registry<>();
    public static final Registry<ProgressProvider> PROGRESS = new Registry<>();
    public static final Registry<FluidInfoProvider> FLUID_INFO = new Registry<>();
    public static final Registry<EnergyInfoProvider> ENERGY_INFO = new Registry<>();

    @Override
    public <T> void addEnergy(int priority, Class<T> clazz, EnergyProvider<T> provider) {
        ENERGY.add(clazz, provider, priority);
    }

    @Override
    public <T> void addFluid(int priority, Class<T> clazz, FluidProvider<T> provider) {
        FLUID.add(clazz, provider, priority);
    }

    @Override
    public void addCauldron(Block block, CauldronProvider provider) {
        CAULDRON.add(block, provider);
    }

    @Override
    public <T> void addCauldron(int priority, Class<T> clazz, CauldronProvider provider) {
        CAULDRON.add(clazz, provider, priority);
    }

    @Override
    public <T> void addItem(int priority, Class<T> clazz, ItemProvider<T> provider) {
        INVENTORY.add(clazz, provider, priority);

    }

    @Override
    public <T> void addProgress(int priority, Class<T> clazz, ProgressProvider<T> provider) {
        PROGRESS.add(clazz, provider, priority);
    }

    @Override
    public void addEnergyInfo(String namespace, int color, String unit, Text name) {
        ENERGY_INFO.add(namespace, new EnergyInfoProvider() {
            @Override
            public Text getName() {
                return name;
            }

            @Override
            public int getColor() {
                return color;
            }

            @Nullable
            @Override
            public String getUnit() {
                return unit;
            }
        });
    }

    @Override
    public <T> void addEnergyInfo(Class<T> clazz, EnergyInfoProvider<T> provider, int priority) {
        ENERGY_INFO.add(clazz, provider, priority);
    }

    @Override
    public <T> void addFluidInfo(Class<T> clazz, FluidInfoProvider<T> provider, int priority) {
        FLUID_INFO.add(clazz, provider, priority);
    }

    @Override
    public void addFluidInfo(Fluid fluid, int color, Text name) {
        FLUID_INFO.add(fluid, new FluidInfoProvider() {
            @Override
            public int getColor() {
                return color;
            }

            @Override
            public Text getName() {
                return name;
            }
        });
    }
}
