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
import lol.bai.megane.runtime.data.wrapper.EnergyWrapper;
import lol.bai.megane.runtime.data.wrapper.FluidWrapper;
import lol.bai.megane.runtime.data.wrapper.ItemWrapper;
import lol.bai.megane.runtime.data.wrapper.ProgressWrapper;
import lol.bai.megane.runtime.util.MeganeUtils;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.data.EnergyData;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.text.Text;

@SuppressWarnings({"rawtypes", "deprecation"})
public enum Registrar implements CommonRegistrar, ClientRegistrar {

    INSTANCE;

    public IRegistrar waila;

    public static final Registry<EnergyProvider> ENERGY = new Registry<>();
    public static final Registry<FluidProvider> FLUID = new Registry<>();
    public static final Registry<CauldronProvider> CAULDRON = new Registry<>();
    public static final Registry<ItemProvider> INVENTORY = new Registry<>();
    public static final Registry<ProgressProvider> PROGRESS = new Registry<>();
    public static final Registry<FluidInfoProvider> FLUID_INFO = new Registry<>();
    public static final Registry<EnergyInfoProvider> ENERGY_INFO = new Registry<>();

    @Override
    public <T> void addEnergy(int priority, Class<T> clazz, EnergyProvider<T> provider) {
        waila.addBlockData(new EnergyWrapper(provider), clazz, priority);
    }

    @Override
    public <T> void addFluid(int priority, Class<T> clazz, FluidProvider<T> provider) {
        waila.addBlockData(new FluidWrapper(provider), clazz, priority);
    }

    @Override
    public void addCauldron(Block block, CauldronProvider provider) {
        untranslatable("StaticCauldronProvider", net.minecraft.util.registry.Registry.BLOCK.getId(block));
    }

    @Override
    public <T> void addCauldron(int priority, Class<T> clazz, CauldronProvider provider) {
        untranslatable("ClassCauldronProvider", clazz);
    }

    @Override
    public <T> void addItem(int priority, Class<T> clazz, ItemProvider<T> provider) {
        waila.addBlockData(new ItemWrapper<>(provider, false), clazz, priority);
        waila.addEntityData(new ItemWrapper<>(provider, true), clazz, priority);
    }

    @Override
    public <T> void addProgress(int priority, Class<T> clazz, ProgressProvider<T> provider) {
        waila.addBlockData(new ProgressWrapper(provider), clazz, priority);
    }

    @Override
    public void addEnergyInfo(String namespace, int color, String unit, Text name) {
        EnergyData.describe(namespace).color(color).unit(unit).name(name);
    }

    @Override
    public <T> void addEnergyInfo(Class<T> clazz, EnergyInfoProvider<T> provider, int priority) {
        untranslatable("ClassEnergyInfo", clazz);
    }

    @Override
    public <T> void addFluidInfo(Class<T> clazz, FluidInfoProvider<T> provider, int priority) {
        untranslatable("ClassFluidInfo", clazz);
    }

    @Override
    public void addFluidInfo(Fluid fluid, int color, Text name) {
        untranslatable("StaticFluidInfo", net.minecraft.util.registry.Registry.FLUID.getId(fluid));
    }

    @Override
    public <T extends Fluid> void addFluidInfo(T fluid, FluidInfoProvider<T> provider) {
        untranslatable("ContextAwareStaticFluidInfo", net.minecraft.util.registry.Registry.FLUID.getId(fluid));
    }

    private void untranslatable(String megane, Object ctx) {
        MeganeUtils.LOGGER.error("[megane] Unable to translate {} for {}", megane, ctx);
    }

}
