package badasintended.megane.impl;

import badasintended.megane.api.MeganeModule;
import badasintended.megane.api.provider.EnergyProvider;
import badasintended.megane.api.registry.MeganeRegistrar;
import dev.technici4n.fasttransferlib.api.energy.EnergyIo;
import net.minecraft.block.entity.BlockEntity;

public class FastTransferLib implements MeganeModule {

    @Override
    public void register(MeganeRegistrar registrar) {
        registrar.energy(1100, BlockEntity.class, EnergyProvider.of(
            t -> t instanceof EnergyIo,
            t -> ((EnergyIo) t).getEnergy(),
            t -> ((EnergyIo) t).getEnergyCapacity()
        ));
    }

}
