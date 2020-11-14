package badasintended.megane.api.registry;

import java.util.HashMap;
import java.util.Map;

import badasintended.megane.api.provider.EnergyProvider;
import badasintended.megane.api.provider.FluidInfoProvider;
import badasintended.megane.api.provider.FluidProvider;
import badasintended.megane.api.provider.InventoryProvider;
import badasintended.megane.api.provider.ProgressProvider;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public class TooltipRegistry {

    public static final BaseTooltipRegistry<BlockEntity, EnergyProvider<? extends BlockEntity>> ENERGY = new BaseTooltipRegistry<>(BlockEntity.class);
    public static final BaseTooltipRegistry<BlockEntity, FluidProvider<? extends BlockEntity>> FLUID = new BaseTooltipRegistry<>(BlockEntity.class);
    public static final BaseTooltipRegistry<BlockEntity, InventoryProvider<? extends BlockEntity>> BLOCK_INVENTORY = new BaseTooltipRegistry<>(BlockEntity.class);
    public static final BaseTooltipRegistry<LivingEntity, InventoryProvider<? extends LivingEntity>> ENTITY_INVENTORY = new BaseTooltipRegistry<>(LivingEntity.class);
    public static final BaseTooltipRegistry<BlockEntity, ProgressProvider<? extends BlockEntity>> PROGRESS = new BaseTooltipRegistry<>(BlockEntity.class);

    @Environment(EnvType.CLIENT)
    public static final FluidInfoRegistry FLUID_INFO = new FluidInfoRegistry();

    @Environment(EnvType.CLIENT)
    public static class FluidInfoRegistry extends BaseTooltipRegistry<Fluid, FluidInfoProvider<? extends Fluid>> {

        private final Map<Fluid, FluidInfoProvider<? extends Fluid>> objEntries = new HashMap<>();

        private FluidInfoRegistry() {
            super(Fluid.class);
        }

        public void register(Fluid fluid, int color, Text name) {
            objEntries.put(fluid, FluidInfoProvider.of(color, name));
        }

        @Nullable
        @Override
        public FluidInfoProvider<? extends Fluid> get(Fluid fluid) {
            return objEntries.containsKey(fluid) ? objEntries.get(fluid) : super.get(fluid);
        }

    }

}
