package lol.bai.megane.module.extragenerators;

import io.github.lucaargolo.extragenerators.common.blockentity.ColorfulGeneratorBlockEntity;
import io.github.lucaargolo.extragenerators.common.blockentity.FluidGeneratorBlockEntity;
import io.github.lucaargolo.extragenerators.common.blockentity.FluidItemGeneratorBlockEntity;
import io.github.lucaargolo.extragenerators.common.blockentity.ItemGeneratorBlockEntity;
import lol.bai.megane.module.extragenerators.provider.GeneratorProvider;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;

@SuppressWarnings("UnstableApiUsage")
public class MeganeExtraGenerators implements IWailaPlugin {

    @Override
    public void register(IRegistrar registrar) {
        registrar.addBlockData(GeneratorProvider
            .builder(ItemGeneratorBlockEntity.class)
            .item(ItemGeneratorBlockEntity::getItemInv)
            .fuel(ItemGeneratorBlockEntity::getBurningFuel, 0)
            .build(), ItemGeneratorBlockEntity.class);

        registrar.addBlockData(GeneratorProvider
            .builder(ColorfulGeneratorBlockEntity.class)
            .item(ColorfulGeneratorBlockEntity::getItemInv)
            .fuel(ColorfulGeneratorBlockEntity::getBurningFuel, 0, 1, 2)
            .build(), ColorfulGeneratorBlockEntity.class);

        registrar.addBlockData(GeneratorProvider
            .builderWithFluid(FluidGeneratorBlockEntity.class)
            .item(FluidGeneratorBlockEntity::getItemInv)
            .fluid(FluidGeneratorBlockEntity::getFluidInv)
            .fuel(FluidGeneratorBlockEntity::getBurningFuel, 0, 1)
            .build(), FluidGeneratorBlockEntity.class);

        registrar.addBlockData(GeneratorProvider
            .builderWithFluid(FluidItemGeneratorBlockEntity.class)
            .item(FluidItemGeneratorBlockEntity::getItemInv)
            .fluid(FluidItemGeneratorBlockEntity::getFluidInv)
            .fuel(FluidItemGeneratorBlockEntity::getBurningFuel, 0, 1, 2)
            .build(), FluidItemGeneratorBlockEntity.class);
    }

}
