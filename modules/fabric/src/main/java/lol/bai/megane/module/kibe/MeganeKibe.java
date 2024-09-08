package lol.bai.megane.module.kibe;

import io.github.lucaargolo.kibe.blocks.entangledtank.EntangledTankEntity;
import io.github.lucaargolo.kibe.blocks.miscellaneous.FluidHopperBlockEntity;
import io.github.lucaargolo.kibe.blocks.tank.TankBlockEntity;
import io.github.lucaargolo.kibe.blocks.vacuum.VacuumHopperEntity;
import lol.bai.megane.module.kibe.provider.TankProvider;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;

@SuppressWarnings("UnstableApiUsage")
public class MeganeKibe implements IWailaPlugin {

    @Override
    public void register(IRegistrar registrar) {
        registrar.addBlockData(new TankProvider<>(TankBlockEntity::getTank), TankBlockEntity.class);
        registrar.addBlockData(new TankProvider<>(EntangledTankEntity::getTank), EntangledTankEntity.class);
        registrar.addBlockData(new TankProvider<>(FluidHopperBlockEntity::getTank), FluidHopperBlockEntity.class);
        registrar.addBlockData(new TankProvider<>(VacuumHopperEntity::getTank), VacuumHopperEntity.class);
    }

}
