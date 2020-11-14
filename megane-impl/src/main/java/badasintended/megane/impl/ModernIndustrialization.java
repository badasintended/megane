package badasintended.megane.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import aztech.modern_industrialization.blocks.creativetank.CreativeTankBlockEntity;
import aztech.modern_industrialization.blocks.tank.TankBlockEntity;
import aztech.modern_industrialization.fluid.CraftingFluid;
import aztech.modern_industrialization.inventory.ConfigurableFluidStack;
import aztech.modern_industrialization.inventory.ConfigurableItemStack;
import aztech.modern_industrialization.machines.impl.MachineBlockEntity;
import aztech.modern_industrialization.machines.impl.MachineFactory;
import aztech.modern_industrialization.machines.impl.multiblock.HatchBlockEntity;
import aztech.modern_industrialization.machines.impl.multiblock.MultiblockMachineBlockEntity;
import badasintended.megane.api.MeganeModule;
import badasintended.megane.api.provider.EnergyProvider;
import badasintended.megane.api.provider.FluidInfoProvider;
import badasintended.megane.api.provider.FluidProvider;
import badasintended.megane.api.provider.InventoryProvider;
import badasintended.megane.api.provider.ProgressProvider;
import badasintended.megane.impl.mixin.modern_industrialization.ACreativeTankBlockEntity;
import badasintended.megane.impl.mixin.modern_industrialization.AMachineBlockEntity;
import badasintended.megane.impl.mixin.modern_industrialization.AMultiblockMachineBlockEntity;
import badasintended.megane.impl.mixin.modern_industrialization.ATankBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static badasintended.megane.api.registry.TooltipRegistry.BLOCK_INVENTORY;
import static badasintended.megane.api.registry.TooltipRegistry.ENERGY;
import static badasintended.megane.api.registry.TooltipRegistry.FLUID;
import static badasintended.megane.api.registry.TooltipRegistry.FLUID_INFO;
import static badasintended.megane.api.registry.TooltipRegistry.PROGRESS;
import static badasintended.megane.util.MeganeUtils.intRange;

public class ModernIndustrialization implements MeganeModule {

    @Override
    public void initialize() {
        ENERGY.register(MachineBlockEntity.class, EnergyProvider.of(
            t -> ((AMachineBlockEntity) t).getMaxEu() > 0,
            t -> (double) ((AMachineBlockEntity) t).getStoredEu(),
            t -> (double) ((AMachineBlockEntity) t).getMaxEu()
        ));

        FLUID.register(MachineBlockEntity.class, FluidProvider.of(
            t -> t.getFluidStacks().size(),
            (t, i) -> t.getFluidStacks().get(i).getFluid().getRawFluid(),
            (t, i) -> (double) t.getFluidStacks().get(i).getAmount(),
            (t, i) -> (double) t.getFluidStacks().get(i).getCapacity()
        ));

        FLUID.register(TankBlockEntity.class, FluidProvider.of(
            t -> 1,
            (t, i) -> ((ATankBlockEntity) t).getFluid().getRawFluid(),
            (t, i) -> (double) ((ATankBlockEntity) t).getAmount(),
            (t, i) -> (double) ((ATankBlockEntity) t).getCapacity()
        ));

        FLUID.register(CreativeTankBlockEntity.class, FluidProvider.of(
            t -> 1,
            (t, i) -> ((ACreativeTankBlockEntity) t).getFluid().getRawFluid(),
            (t, i) -> -1D, (t, i) -> -1D
        ));

        BLOCK_INVENTORY.register(MachineBlockEntity.class, InventoryProvider.of(
            t -> t.getItemStacks().size(),
            (t, i) -> t.getItemStacks().get(i).getStack()
        ));

        PROGRESS.register(MachineBlockEntity.class, new ProgressProvider<MachineBlockEntity>() {
            private int inputSize = 0;

            @Override
            public boolean hasProgress(MachineBlockEntity t) {
                return ((AMachineBlockEntity) t).getRecipeEnergy() > 0;
            }

            @Override
            public int[] getInputSlots(MachineBlockEntity t) {
                inputSize = t.getItemInputStacks().size();
                return intRange(0, inputSize);
            }

            @Override
            public int[] getOutputSlots(MachineBlockEntity t) {
                return intRange(inputSize, inputSize + t.getItemOutputStacks().size());
            }

            @Override
            public @NotNull ItemStack getStack(MachineBlockEntity t, int i) {
                return i < inputSize ? t.getItemInputStacks().get(i).getStack() : t.getItemOutputStacks().get(i - inputSize).getStack();
            }

            @Override
            public int getPercentage(MachineBlockEntity t) {
                AMachineBlockEntity accessor = (AMachineBlockEntity) t;
                MachineFactory factory = accessor.getFactory();
                int val = (int) ((double) accessor.getUsedEnergy() / (double) accessor.getRecipeEnergy() * 100);
                return factory.isProgressBarFlipped() ? 100 - val : val;
            }
        });

        FLUID.register(MultiblockMachineBlockEntity.class, new FluidProvider<MultiblockMachineBlockEntity>() {
            private List<ConfigurableFluidStack> fluids;

            @Override
            public boolean hasFluid(MultiblockMachineBlockEntity t) {
                Map<BlockPos, HatchBlockEntity> hatches = ((AMultiblockMachineBlockEntity) t).getLinkedHatches();
                fluids = hatches.values().stream().filter(Objects::nonNull).map(MachineBlockEntity::getFluidStacks).flatMap(Collection::stream).collect(Collectors.toList());
                return !fluids.isEmpty();
            }

            @Override
            public int getSlotCount(MultiblockMachineBlockEntity t) {
                return fluids.size();
            }

            @Nullable
            @Override
            public Fluid getFluid(MultiblockMachineBlockEntity multiblockMachineBlockEntity, int slot) {
                return fluids.get(slot).getFluid().getRawFluid();
            }

            @Override
            public double getStored(MultiblockMachineBlockEntity t, int i) {
                return fluids.get(i).getAmount();
            }

            @Override
            public double getMax(MultiblockMachineBlockEntity t, int i) {
                return fluids.get(i).getCapacity();
            }
        });

        BLOCK_INVENTORY.register(MultiblockMachineBlockEntity.class, new InventoryProvider<MultiblockMachineBlockEntity>() {
            private List<ConfigurableItemStack> stacks;

            @Override
            public boolean hasInventory(MultiblockMachineBlockEntity t) {
                Map<BlockPos, HatchBlockEntity> hatches = ((AMultiblockMachineBlockEntity) t).getLinkedHatches();
                stacks = hatches.values().stream().filter(Objects::nonNull).map(MachineBlockEntity::getItemStacks).flatMap(Collection::stream).collect(Collectors.toList());
                return !stacks.isEmpty();
            }

            @Override
            public int size(MultiblockMachineBlockEntity t) {
                return stacks.size();
            }

            @Override
            public @NotNull ItemStack getStack(MultiblockMachineBlockEntity t, int i) {
                return stacks.get(i).getStack();
            }
        });
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void initializeClient() {
        FLUID_INFO.register(CraftingFluid.class, FluidInfoProvider.of(f -> f.color, f -> f.key.name));
    }

}
