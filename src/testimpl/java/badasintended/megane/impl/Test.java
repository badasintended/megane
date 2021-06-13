package badasintended.megane.impl;

import badasintended.megane.api.MeganeModule;
import badasintended.megane.api.provider.EnergyProvider;
import badasintended.megane.api.provider.FluidProvider;
import badasintended.megane.api.registry.MeganeRegistrar;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import org.jetbrains.annotations.Nullable;

public class Test implements MeganeModule {

    @Override
    public void register(MeganeRegistrar registrar) {
        registrar
            .energy(ChestBlockEntity.class, new EnergyProvider<>() {
                double stored;

                @Override
                public double getStored(ChestBlockEntity chestBlockEntity) {
                    stored = stored + 100;
                    if (stored >= 10000) {
                        stored = 0;
                    }
                    return stored;
                }

                @Override
                public double getMax(ChestBlockEntity chestBlockEntity) {
                    return 10000;
                }
            })
            .fluid(ChestBlockEntity.class, new FluidProvider<>() {
                double stored;

                @Override
                public int getSlotCount(ChestBlockEntity chestBlockEntity) {
                    return 2;
                }

                @Override
                public @Nullable Fluid getFluid(ChestBlockEntity chestBlockEntity, int slot) {
                    return slot == 0 ? Fluids.WATER : Fluids.LAVA;
                }

                @Override
                public double getStored(ChestBlockEntity chestBlockEntity, int slot) {
                    stored = stored + 100;
                    if (stored >= 10000) {
                        stored = 0;
                    }
                    return stored;
                }

                @Override
                public double getMax(ChestBlockEntity chestBlockEntity, int slot) {
                    stored = stored + 100;
                    if (stored >= 10000) {
                        stored = 0;
                    }
                    return stored;
                }
            });
    }

}
