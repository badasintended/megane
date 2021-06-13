package badasintended.megane.impl;

import badasintended.megane.api.MeganeModule;
import badasintended.megane.api.provider.EnergyProvider;
import badasintended.megane.api.registry.MeganeRegistrar;
import net.minecraft.block.entity.BlockEntity;
import team.reborn.energy.Energy;
import team.reborn.energy.EnergyHandler;

public class TeamRebornEnergy implements MeganeModule {

    @Override
    public void register(MeganeRegistrar registrar) {
        registrar.energy(1200, BlockEntity.class, new EnergyProvider<>() {
            EnergyHandler handler;

            @Override
            public boolean hasEnergy(BlockEntity blockEntity) {
                boolean valid = Energy.valid(blockEntity);
                if (valid) {
                    handler = Energy.of(blockEntity);
                }
                return valid;
            }

            @Override
            public double getStored(BlockEntity blockEntity) {
                return handler.getEnergy();
            }

            @Override
            public double getMax(BlockEntity blockEntity) {
                return handler.getMaxStored();
            }
        });
    }

}
