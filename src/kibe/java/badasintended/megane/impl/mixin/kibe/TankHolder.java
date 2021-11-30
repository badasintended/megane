package badasintended.megane.impl.mixin.kibe;

import io.github.lucaargolo.kibe.blocks.entangledtank.EntangledTankEntity;
import io.github.lucaargolo.kibe.blocks.miscellaneous.FluidHopperBlockEntity;
import io.github.lucaargolo.kibe.blocks.tank.TankBlockEntity;
import io.github.lucaargolo.kibe.blocks.vacuum.VacuumHopperEntity;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({
    TankBlockEntity.class,
    EntangledTankEntity.class,
    FluidHopperBlockEntity.class,
    VacuumHopperEntity.class
})
public interface TankHolder {

    @Invoker("getTank")
    @SuppressWarnings("UnstableApiUsage")
    SingleVariantStorage<FluidVariant> megane_getTank();

}
