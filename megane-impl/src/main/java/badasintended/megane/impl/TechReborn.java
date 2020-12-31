package badasintended.megane.impl;

import badasintended.megane.api.MeganeModule;
import badasintended.megane.api.provider.FluidProvider;
import badasintended.megane.api.provider.ProgressProvider;
import badasintended.megane.impl.util.A;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import reborncore.common.blockentity.MachineBaseBlockEntity;
import techreborn.blockentity.generator.BaseFluidGeneratorBlockEntity;
import techreborn.blockentity.generator.basic.SolidFuelGeneratorBlockEntity;
import techreborn.blockentity.machine.GenericMachineBlockEntity;
import techreborn.blockentity.machine.iron.IronAlloyFurnaceBlockEntity;
import techreborn.blockentity.machine.iron.IronFurnaceBlockEntity;
import techreborn.blockentity.machine.multiblock.FusionControlComputerBlockEntity;
import techreborn.blockentity.machine.tier1.ElectricFurnaceBlockEntity;
import techreborn.blockentity.machine.tier1.RollingMachineBlockEntity;
import techreborn.blockentity.machine.tier3.MatterFabricatorBlockEntity;
import techreborn.init.ModFluids;

import static badasintended.megane.api.registry.TooltipRegistry.ENERGY_INFO;
import static badasintended.megane.api.registry.TooltipRegistry.FLUID;
import static badasintended.megane.api.registry.TooltipRegistry.FLUID_INFO;
import static badasintended.megane.api.registry.TooltipRegistry.PROGRESS;
import static techreborn.init.ModFluids.BERYLLIUM;
import static techreborn.init.ModFluids.BIOFUEL;
import static techreborn.init.ModFluids.CALCIUM;
import static techreborn.init.ModFluids.CALCIUM_CARBONATE;
import static techreborn.init.ModFluids.CARBON;
import static techreborn.init.ModFluids.CARBON_FIBER;
import static techreborn.init.ModFluids.CHLORITE;
import static techreborn.init.ModFluids.COMPRESSED_AIR;
import static techreborn.init.ModFluids.DEUTERIUM;
import static techreborn.init.ModFluids.DIESEL;
import static techreborn.init.ModFluids.ELECTROLYZED_WATER;
import static techreborn.init.ModFluids.GLYCERYL;
import static techreborn.init.ModFluids.HELIUM;
import static techreborn.init.ModFluids.HELIUM3;
import static techreborn.init.ModFluids.HELIUMPLASMA;
import static techreborn.init.ModFluids.HYDROGEN;
import static techreborn.init.ModFluids.LITHIUM;
import static techreborn.init.ModFluids.MERCURY;
import static techreborn.init.ModFluids.METHANE;
import static techreborn.init.ModFluids.NITROCOAL_FUEL;
import static techreborn.init.ModFluids.NITROFUEL;
import static techreborn.init.ModFluids.NITROGEN;
import static techreborn.init.ModFluids.NITROGEN_DIOXIDE;
import static techreborn.init.ModFluids.NITRO_CARBON;
import static techreborn.init.ModFluids.NITRO_DIESEL;
import static techreborn.init.ModFluids.OIL;
import static techreborn.init.ModFluids.POTASSIUM;
import static techreborn.init.ModFluids.SILICON;
import static techreborn.init.ModFluids.SODIUM;
import static techreborn.init.ModFluids.SODIUM_PERSULFATE;
import static techreborn.init.ModFluids.SODIUM_SULFIDE;
import static techreborn.init.ModFluids.SULFUR;
import static techreborn.init.ModFluids.SULFURIC_ACID;
import static techreborn.init.ModFluids.TRITIUM;
import static techreborn.init.ModFluids.WOLFRAMIUM;

public class TechReborn implements MeganeModule {

    private static final int[] A_012345 = new int[]{0, 1, 2, 3, 4, 5};
    private static final int[] A_6789AB = new int[]{6, 7, 8, 9, 10, 11};
    private static final int[] A_9 = new int[]{9};

    @Override
    @SuppressWarnings({"ConstantConditions", "deprecation"})
    public void initialize() {
        PROGRESS.register(GenericMachineBlockEntity.class, ProgressProvider.of(
            b -> b.getRecipeCrafter() != null,
            b -> b.getRecipeCrafter().inputSlots == null ? A.A : b.getRecipeCrafter().inputSlots,
            b -> b.getRecipeCrafter().outputSlots == null ? A.A : b.getRecipeCrafter().outputSlots,
            MachineBaseBlockEntity::getStack, b -> b.getProgressScaled(100)
        ));

        PROGRESS.register(FusionControlComputerBlockEntity.class, ProgressProvider.of(
            b -> A.A_01, b -> A.A_2, (b, i) -> b.inventory.getStack(i), b -> b.getProgressScaled(100)
        ));

        PROGRESS.register(ElectricFurnaceBlockEntity.class, ProgressProvider.of(
            b -> A.A_0, b -> A.A_1, (b, i) -> b.inventory.getStack(i), b -> b.getProgressScaled(100)
        ));

        PROGRESS.register(IronAlloyFurnaceBlockEntity.class, ProgressProvider.of(
            b -> A.A_01, b -> A.A_2, (b, i) -> b.inventory.getStack(i), b -> b.getProgressScaled(100)
        ));

        PROGRESS.register(IronFurnaceBlockEntity.class, ProgressProvider.of(
            b -> A.A_0, b -> A.A_1, (b, i) -> b.inventory.getStack(i), b -> b.getProgressScaled(100)
        ));

        PROGRESS.register(MatterFabricatorBlockEntity.class, ProgressProvider.of(
            b -> A_012345, b -> A_6789AB, (b, i) -> b.inventory.getStack(i), b -> b.getProgressScaled(100)
        ));

        PROGRESS.register(RollingMachineBlockEntity.class, ProgressProvider.of(
            b -> b.craftingSlots, b -> A_9, (b, i) -> b.getInventory().getStack(i), b -> b.getProgressScaled(100)
        ));

        PROGRESS.register(BaseFluidGeneratorBlockEntity.class, ProgressProvider.of(
            b -> A.A_01, b -> A.A, (b, i) -> b.getInventory().getStack(i), b -> b.getProgressScaled(10)
        ));

        PROGRESS.register(SolidFuelGeneratorBlockEntity.class, ProgressProvider.of(
            b -> A.A_0, b -> A.A, (b, i) -> b.getInventory().getStack(i), b -> 100 - b.getScaledBurnTime(100) == 0 ? 100 : b.getScaledBurnTime(100)
        ));

        FLUID.register(MachineBaseBlockEntity.class, FluidProvider.of(
            MachineBaseBlockEntity::showTankConfig, t -> 1,
            (t, i) -> t.getTank().getFluid(),
            (t, i) -> (double) t.getTank().getFluidAmount().getRawValue(),
            (t, i) -> (double) t.getTank().getCapacity().getRawValue()
        ));
    }

    private void fc(ModFluids fluid, int color) {
        FLUID_INFO.register(fluid.getFluid(), color, fluid.getBlock().getName());
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void initializeClient() {
        // haha yes, hardcoded fluid color!
        fc(BERYLLIUM, 0x082507);
        fc(BIOFUEL, 0x156718);
        fc(CALCIUM, 0x6D4539);
        fc(CALCIUM_CARBONATE, 0x5A250C);
        fc(CARBON, 0x0D0F0B);
        fc(CARBON_FIBER, 0x0A0B0D);
        fc(CHLORITE, 0x255D5F);
        fc(COMPRESSED_AIR, 0x393D42);
        fc(DEUTERIUM, 0xB5BA16);
        fc(DIESEL, 0x97681D);
        fc(ELECTROLYZED_WATER, 0x0A1546);
        fc(GLYCERYL, 0x084942);
        fc(HELIUM, 0xB6B850);
        fc(HELIUM3, 0xB8BA5A);
        fc(HELIUMPLASMA, 0xDFDB95);
        fc(HYDROGEN, 0x09174E);
        fc(LITHIUM, 0x41739C);
        fc(MERCURY, 0x858688);
        fc(METHANE, 0x881258);
        fc(NITRO_CARBON, 0x431E1A);
        fc(NITRO_DIESEL, 0xD1553B);
        fc(NITROCOAL_FUEL, 0x0A201D);
        fc(NITROFUEL, 0xA2B30C);
        fc(NITROGEN, 0x1F8A81);
        fc(NITROGEN_DIOXIDE, 0x5FBDB7);
        fc(OIL, 0x171717);
        fc(POTASSIUM, 0x748A8B);
        fc(SILICON, 0x180A24);
        fc(SODIUM, 0x0A2276);
        fc(SODIUM_SULFIDE, 0x825F0C);
        fc(SODIUM_PERSULFATE, 0x0B4345);
        fc(SULFUR, 0x843030);
        fc(SULFURIC_ACID, 0x86888B);
        fc(TRITIUM, 0xAF1618);
        fc(WOLFRAMIUM, 0x291F35);

        ENERGY_INFO.register("techreborn", 0x800800, "E");
    }

}
