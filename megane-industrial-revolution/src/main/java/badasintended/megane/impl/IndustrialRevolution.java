package badasintended.megane.impl;

import alexiil.mc.lib.attributes.fluid.volume.FluidKeys;
import badasintended.megane.api.MeganeModule;
import badasintended.megane.api.provider.EnergyProvider;
import badasintended.megane.api.provider.FluidInfoProvider;
import badasintended.megane.api.provider.FluidProvider;
import badasintended.megane.api.provider.ProgressProvider;
import badasintended.megane.api.registry.MeganeClientRegistrar;
import badasintended.megane.api.registry.MeganeRegistrar;
import me.steven.indrev.blockentities.MachineBlockEntity;
import me.steven.indrev.blockentities.crafters.CompressorFactoryBlockEntity;
import me.steven.indrev.blockentities.crafters.CraftingMachineBlockEntity;
import me.steven.indrev.blockentities.crafters.ElectricFurnaceFactoryBlockEntity;
import me.steven.indrev.blockentities.crafters.SolidInfuserFactoryBlockEntity;
import me.steven.indrev.blockentities.modularworkbench.ModularWorkbenchBlockEntity;
import me.steven.indrev.blockentities.storage.TankBlockEntity;
import me.steven.indrev.fluids.BaseFluid;
import net.minecraft.screen.PropertyDelegate;

public class IndustrialRevolution implements MeganeModule {

    private MeganeRegistrar registrar;

    private <T extends MachineBlockEntity<?>> void progress(Class<T> clazz, int amount, int currentIndex, int maxIndex) {
        registrar.progress(clazz, ProgressProvider.of(
            b -> b.getInventoryComponent().getInventory().getInputSlots(),
            b -> b.getInventoryComponent().getInventory().getOutputSlots(),
            (b, i) -> b.getInventoryComponent().getInventory().getStack(i),
            b -> {
                PropertyDelegate property = b.getPropertyDelegate();
                int result = 0;
                int j = 1;
                for (int i = 0; i < amount; i++) {
                    double current = property.get(currentIndex + (i * 2));
                    double max = property.get(maxIndex + (i * 2));
                    result = Math.max(result, (int) (current / max * 100));
                }
                return result;
            }
        ));
    }

    @Override
    public void register(MeganeRegistrar registrar) {
        this.registrar = registrar;
        registrar
            .energy(MachineBlockEntity.class, EnergyProvider.of(
                MachineBlockEntity::getEnergy,
                MachineBlockEntity::getEnergyCapacity
            ))
            .fluid(MachineBlockEntity.class, FluidProvider.of(
                t -> t.getFluidComponent() != null,
                t -> t.getFluidComponent().getTankCount(),
                (t, i) -> t.getFluidComponent().getInvFluid(i).getRawFluid(),
                (t, i) -> (double) t.getFluidComponent().getInvFluid(i).getAmount_F().asInt(1000),
                (t, i) -> (double) t.getFluidComponent().getMaxAmount_F(i).asInt(1000)
            ))
            .fluid(TankBlockEntity.class, FluidProvider.of(
                t -> t.getFluidComponent().getTankCount(),
                (t, i) -> t.getFluidComponent().getInvFluid(i).getRawFluid(),
                (t, i) -> (double) t.getFluidComponent().getInvFluid(i).getAmount_F().asInt(1000),
                (t, i) -> (double) t.getFluidComponent().getMaxAmount_F(i).asInt(1000)
            ));

        progress(CraftingMachineBlockEntity.class, 1, 4, 5);
        progress(CompressorFactoryBlockEntity.class, 5, 4, 5);
        progress(CompressorFactoryBlockEntity.class, 5, 4, 5);
        progress(SolidInfuserFactoryBlockEntity.class, 5, 4, 5);
        progress(ElectricFurnaceFactoryBlockEntity.class, 5, 4, 5);
        progress(ModularWorkbenchBlockEntity.class, 1, 2, 3);
    }

    @Override
    public void registerClient(MeganeClientRegistrar registrar) {
        registrar
            .fluid(BaseFluid.class, FluidInfoProvider.of(f -> FluidKeys.get(f).renderColor, f -> FluidKeys.get(f).name))
            .energy("indrev", 0x3B4ADE, "LF");
    }

}
