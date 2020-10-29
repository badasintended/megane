package badasintended.megane.impl;

import aztech.modern_industrialization.blocks.creativetank.CreativeTankBlockEntity;
import aztech.modern_industrialization.blocks.tank.TankBlockEntity;
import aztech.modern_industrialization.inventory.ConfigurableFluidStack;
import aztech.modern_industrialization.inventory.ConfigurableItemStack;
import aztech.modern_industrialization.machines.impl.MachineBlockEntity;
import aztech.modern_industrialization.machines.impl.MachineFactory;
import aztech.modern_industrialization.machines.impl.multiblock.HatchBlockEntity;
import aztech.modern_industrialization.machines.impl.multiblock.MultiblockMachineBlockEntity;
import badasintended.megane.api.MeganeEntrypoint;
import badasintended.megane.api.provider.*;
import badasintended.megane.impl.mixin.modern_industrialization.*;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

import static badasintended.megane.api.registry.TooltipRegistry.*;
import static badasintended.megane.util.MeganeUtils.intRange;

public class ModernIndustrialization implements MeganeEntrypoint {

    @Override
    public String[] dependencies() {
        return new String[]{"modern_industrialization"};
    }

    @Override
    public void initialize() {
        ENERGY.register(MachineBlockEntity.class, EnergyProvider.of(
            t -> ((AMachineBlockEntity) t).getMaxEu() > 0,
            t -> (double) ((AMachineBlockEntity) t).getStoredEu(),
            t -> (double) ((AMachineBlockEntity) t).getMaxEu()
        ));

        FLUID.register(MachineBlockEntity.class, FluidProvider.of(
            t -> t.getFluidStacks().size(),
            (t, i) -> t.getFluidStacks().get(i).getFluid().name,
            (t, i) -> (double) t.getFluidStacks().get(i).getAmount(),
            (t, i) -> (double) t.getFluidStacks().get(i).getCapacity()
        ));

        FLUID.register(TankBlockEntity.class, FluidProvider.of(
            t -> 1,
            (t, i) -> ((ATankBlockEntity) t).getFluid().name,
            (t, i) -> (double) ((ATankBlockEntity) t).getAmount(),
            (t, i) -> (double) ((ATankBlockEntity) t).getCapacity()
        ));

        FLUID.register(CreativeTankBlockEntity.class, FluidProvider.of(
            t -> 1, (t, i) -> ((ACreativeTankBlockEntity) t).getFluid().name, (t, i) -> -1D, (t, i) -> -1D
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

            @Override
            public Text getFluidName(MultiblockMachineBlockEntity t, int i) {
                return fluids.get(i).getFluid().name;
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

}
