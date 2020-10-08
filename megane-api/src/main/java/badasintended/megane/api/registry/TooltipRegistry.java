package badasintended.megane.api.registry;

import badasintended.megane.api.provider.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;

public final class TooltipRegistry<T, P extends BaseTooltipRegistry.Provider<? extends T>> {

    public static final EnergyTooltipRegistry ENERGY = new EnergyTooltipRegistry();
    public static final FluidTooltipRegistry FLUID = new FluidTooltipRegistry();
    public static final BlockInventoryRegistry BLOCK_INVENTORY = new BlockInventoryRegistry();
    public static final EntityInventoryRegistry ENTITY_INVENTORY = new EntityInventoryRegistry();
    public static final ProgressTooltipRegistry PROGRESS = new ProgressTooltipRegistry();

    public static final class EnergyTooltipRegistry extends BaseTooltipRegistry<BlockEntity, EnergyProvider<? extends BlockEntity>> {

        private EnergyTooltipRegistry() {
            super(BlockEntity.class);
        }

    }

    public static final class FluidTooltipRegistry extends BaseTooltipRegistry<BlockEntity, FluidProvider<? extends BlockEntity>> {

        private FluidTooltipRegistry() {
            super(BlockEntity.class);
        }

    }

    public static final class BlockInventoryRegistry extends BaseTooltipRegistry<BlockEntity, InventoryProvider<? extends BlockEntity>> {

        private BlockInventoryRegistry() {
            super(BlockEntity.class);
        }

    }

    public static final class EntityInventoryRegistry extends BaseTooltipRegistry<LivingEntity, InventoryProvider<? extends LivingEntity>> {

        private EntityInventoryRegistry() {
            super(LivingEntity.class);
        }

    }

    public static final class ProgressTooltipRegistry extends BaseTooltipRegistry<BlockEntity, ProgressProvider<? extends BlockEntity>> {

        private ProgressTooltipRegistry() {
            super(BlockEntity.class);
        }

    }

}
