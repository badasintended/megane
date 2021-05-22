package badasintended.megane.impl;

import java.util.List;

import aztech.modern_industrialization.blocks.creativetank.CreativeTankBlockEntity;
import aztech.modern_industrialization.blocks.tank.TankBlockEntity;
import aztech.modern_industrialization.inventory.ConfigurableFluidStack;
import aztech.modern_industrialization.inventory.ConfigurableItemStack;
import aztech.modern_industrialization.machines.MachineBlockEntity;
import aztech.modern_industrialization.machines.components.CrafterComponent;
import aztech.modern_industrialization.machines.components.EnergyComponent;
import aztech.modern_industrialization.machines.components.MachineInventoryComponent;
import badasintended.megane.api.MeganeModule;
import badasintended.megane.api.provider.EnergyProvider;
import badasintended.megane.api.provider.FluidInfoProvider;
import badasintended.megane.api.provider.FluidProvider;
import badasintended.megane.api.provider.InventoryProvider;
import badasintended.megane.api.provider.ProgressProvider;
import badasintended.megane.api.registry.MeganeClientRegistrar;
import badasintended.megane.api.registry.MeganeRegistrar;
import badasintended.megane.impl.mixin.modern_industrialization.ACraftingFluid;
import badasintended.megane.impl.mixin.modern_industrialization.ATankBlockEntity;
import badasintended.megane.impl.mixin.modern_industrialization.CrafterComponentHolder;
import badasintended.megane.impl.mixin.modern_industrialization.EnergyComponentHolder;
import badasintended.megane.impl.mixin.modern_industrialization.EnergyInputsComponentHolder;
import badasintended.megane.impl.mixin.modern_industrialization.EnergyOutputsComponentHolder;
import badasintended.megane.impl.mixin.modern_industrialization.MultiblockInventoryComponentHolder;
import badasintended.megane.util.MeganeUtils;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ModernIndustrialization implements MeganeModule {

    @Override
    public void register(MeganeRegistrar registrar) {
        registrar
            .energy(EnergyComponentHolder.class, EnergyProvider.of(
                t -> t.getEnergy().getEu(),
                t -> t.getEnergy().getCapacity()
            ))
            .energy(EnergyInputsComponentHolder.class, EnergyProvider.of(
                t -> !t.getEnergyInputs().isEmpty(),
                t -> t.getEnergyInputs().stream().mapToDouble(EnergyComponent::getEu).sum(),
                t -> t.getEnergyInputs().stream().mapToDouble(EnergyComponent::getCapacity).sum()
            ))
            .energy(EnergyOutputsComponentHolder.class, EnergyProvider.of(
                t -> !t.getEnergyOutputs().isEmpty(),
                t -> t.getEnergyOutputs().stream().mapToDouble(EnergyComponent::getEu).sum(),
                t -> t.getEnergyOutputs().stream().mapToDouble(EnergyComponent::getCapacity).sum()
            ))
            .fluid(TankBlockEntity.class, FluidProvider.of(
                t -> 1,
                (t, i) -> t.resource(),
                (t, i) -> t.amount() / 81.0,
                (t, i) -> ((ATankBlockEntity) t).getCapacity() / 81.0
            ))
            .fluid(CreativeTankBlockEntity.class, FluidProvider.of(
                t -> 1,
                (t, i) -> t.resource(),
                (t, i) -> -1,
                (t, i) -> -1
            ))
            .fluid(MachineBlockEntity.class, FluidProvider.of(
                t -> t.getInventory().getFluidStacks().size(),
                (t, i) -> t.getInventory().getFluidStacks().get(i).resource(),
                (t, i) -> t.getInventory().getFluidStacks().get(i).getAmount() / 81.0,
                (t, i) -> t.getInventory().getFluidStacks().get(i).getCapacity() / 81.0
            ))
            .fluid(999, MultiblockInventoryComponentHolder.class, new FluidProvider<MultiblockInventoryComponentHolder>() {
                List<ConfigurableFluidStack> input;
                List<ConfigurableFluidStack> output;

                @Override
                public int getSlotCount(MultiblockInventoryComponentHolder multiblockInventoryComponentHolder) {
                    input = multiblockInventoryComponentHolder.getInventory().getFluidInputs();
                    output = multiblockInventoryComponentHolder.getInventory().getFluidOutputs();
                    return input.size() + output.size();
                }

                ConfigurableFluidStack getFluidStack(int slot) {
                    return slot < input.size()
                        ? input.get(slot)
                        : output.get(slot - input.size());
                }

                @Override
                public @Nullable Fluid getFluid(MultiblockInventoryComponentHolder multiblockInventoryComponentHolder, int slot) {
                    return getFluidStack(slot).getFluid();
                }

                @Override
                public double getStored(MultiblockInventoryComponentHolder multiblockInventoryComponentHolder, int slot) {
                    return getFluidStack(slot).getAmount() / 81.0;
                }

                @Override
                public double getMax(MultiblockInventoryComponentHolder multiblockInventoryComponentHolder, int slot) {
                    return getFluidStack(slot).getCapacity() / 81.0;
                }
            })
            .inventory(MachineBlockEntity.class, InventoryProvider.of(
                t -> t.getInventory().getItemStacks().size(),
                (t, i) -> {
                    ConfigurableItemStack stack = t.getInventory().getItemStacks().get(i);
                    return stack.resource().toStack(stack.getCount());
                }
            ))
            .inventory(999, MultiblockInventoryComponentHolder.class, new InventoryProvider<MultiblockInventoryComponentHolder>() {
                List<ConfigurableItemStack> input;
                List<ConfigurableItemStack> output;

                @Override
                public int size(MultiblockInventoryComponentHolder multiblockInventoryComponentHolder) {
                    input = multiblockInventoryComponentHolder.getInventory().getItemInputs();
                    output = multiblockInventoryComponentHolder.getInventory().getItemOutputs();
                    return input.size() + output.size();
                }

                @Override
                public @NotNull ItemStack getStack(MultiblockInventoryComponentHolder multiblockInventoryComponentHolder, int slot) {
                    ConfigurableItemStack stack = slot < input.size()
                        ? input.get(slot)
                        : output.get(slot - input.size());
                    return stack.resource().toStack(stack.getCount());
                }
            })
            .progress(CrafterComponentHolder.class, new ProgressProvider<CrafterComponentHolder>() {
                CrafterComponent crafter;
                CrafterComponent.Inventory inventory;

                int inputCount;

                boolean isNotMultiBlock = false;
                MachineInventoryComponent machineInventoryComponent;

                @Override
                public int[] getInputSlots(CrafterComponentHolder crafterComponentHolder) {
                    crafter = crafterComponentHolder.getCrafter();
                    inventory = ((CrafterComponentHolder.Inventory) crafter).getInventory();

                    isNotMultiBlock = inventory instanceof MachineInventoryComponent;

                    if (isNotMultiBlock) {
                        machineInventoryComponent = (MachineInventoryComponent) inventory;
                        inputCount = machineInventoryComponent.itemInputCount;
                    } else {
                        inputCount = inventory.getItemInputs().size();
                    }

                    return MeganeUtils.intRange(0, inputCount);
                }

                @Override
                public int[] getOutputSlots(CrafterComponentHolder crafterComponentHolder) {
                    return MeganeUtils.intRange(inputCount, isNotMultiBlock
                        ? machineInventoryComponent.itemOutputCount
                        : inventory.getItemOutputs().size());
                }

                @Override
                public @NotNull ItemStack getStack(CrafterComponentHolder crafterComponentHolder, int slot) {
                    ConfigurableItemStack stack;
                    if (isNotMultiBlock) {
                        stack = machineInventoryComponent.inventory.getItemStacks().get(slot);
                    } else {
                        stack = slot < inputCount
                            ? inventory.getItemInputs().get(slot)
                            : inventory.getItemOutputs().get(slot);
                    }
                    return stack.getItemKey().toStack(stack.getCount());
                }

                @Override
                public int getPercentage(CrafterComponentHolder crafterComponentHolder) {
                    return (int) (crafter.getProgress() * 100);
                }
            });
    }

    @Override
    public void registerClient(MeganeClientRegistrar registrar) {
        registrar
            .fluid(ACraftingFluid.class, FluidInfoProvider.of(f -> f.getBlock().getColor(), f -> f.getBlock().getName()))
            .energy("modern_industrialization", 0xB70000, "EU");
    }

}
