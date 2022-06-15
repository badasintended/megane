package lol.bai.megane.module.kibe;

import io.github.lucaargolo.kibe.blocks.entangledtank.EntangledTankEntity;
import io.github.lucaargolo.kibe.blocks.miscellaneous.FluidHopperBlockEntity;
import io.github.lucaargolo.kibe.blocks.tank.TankBlockEntity;
import io.github.lucaargolo.kibe.blocks.vacuum.VacuumHopperEntity;
import io.github.lucaargolo.kibe.fluids.miscellaneous.LiquidXpFluid;
import lol.bai.megane.api.MeganeModule;
import lol.bai.megane.api.registry.ClientRegistrar;
import lol.bai.megane.api.registry.CommonRegistrar;
import lol.bai.megane.module.kibe.provider.ModdedFluidInfoProvider;
import lol.bai.megane.module.kibe.provider.TankFluidProvider;

@SuppressWarnings("UnstableApiUsage")
public class MeganeKibe implements MeganeModule {

    @Override
    public void registerCommon(CommonRegistrar registrar) {
        registrar.addFluid(TankBlockEntity.class, new TankFluidProvider<>(TankBlockEntity::getTank));
        registrar.addFluid(EntangledTankEntity.class, new TankFluidProvider<>(EntangledTankEntity::getTank));
        registrar.addFluid(FluidHopperBlockEntity.class, new TankFluidProvider<>(FluidHopperBlockEntity::getTank));
        registrar.addFluid(VacuumHopperEntity.class, new TankFluidProvider<>(VacuumHopperEntity::getTank));
    }

    @Override
    public void registerClient(ClientRegistrar registrar) {
        registrar.addFluidInfo(LiquidXpFluid.class, new ModdedFluidInfoProvider<>(0x55FF55));
    }

}
