package badasintended.megane.api.registry;

import badasintended.megane.api.provider.EnergyInfoProvider;
import badasintended.megane.api.provider.EnergyProvider;
import badasintended.megane.api.provider.FluidInfoProvider;
import badasintended.megane.api.provider.FluidProvider;
import badasintended.megane.api.provider.InventoryProvider;
import badasintended.megane.api.provider.ProgressProvider;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.text.Text;

public class TooltipRegistry {

    public static final BaseTooltipRegistry<BlockEntity, EnergyProvider<? extends BlockEntity>> ENERGY = new BaseTooltipRegistry<>(BlockEntity.class);
    public static final BaseTooltipRegistry<BlockEntity, FluidProvider<? extends BlockEntity>> FLUID = new BaseTooltipRegistry<>(BlockEntity.class);
    public static final BaseTooltipRegistry<BlockEntity, InventoryProvider<? extends BlockEntity>> BLOCK_INVENTORY = new BaseTooltipRegistry<>(BlockEntity.class);
    public static final BaseTooltipRegistry<LivingEntity, InventoryProvider<? extends LivingEntity>> ENTITY_INVENTORY = new BaseTooltipRegistry<>(LivingEntity.class);
    public static final BaseTooltipRegistry<BlockEntity, ProgressProvider<? extends BlockEntity>> PROGRESS = new BaseTooltipRegistry<>(BlockEntity.class);

    /**
     * only available on client, will throws NPE when used on server
     */
    public static final FluidInfoRegistry FLUID_INFO;
    public static final EnergyInfoRegistry ENERGY_INFO;

    static {
        EnvType env = FabricLoader.getInstance().getEnvironmentType();
        FLUID_INFO = env == EnvType.CLIENT ? new FluidInfoRegistry() : null;
        ENERGY_INFO = env == EnvType.CLIENT ? new EnergyInfoRegistry() : null;
    }

    @Environment(EnvType.CLIENT)
    public static class FluidInfoRegistry extends BaseTooltipRegistry<Fluid, FluidInfoProvider<? extends Fluid>> {

        private FluidInfoRegistry() {
            super(Fluid.class);
        }

        public void register(Fluid fluid, int color, Text name) {
            objEntries.put(fluid, FluidInfoProvider.of(color, name));
        }

    }

    public static class EnergyInfoRegistry extends BaseTooltipRegistry<String, EnergyInfoProvider<String>> {

        EnergyInfoRegistry() {
            super(String.class);
        }

        public void register(String namespace, int color, String unit) {
            objEntries.put(namespace, EnergyInfoProvider.of(color, unit));
        }

    }

}
