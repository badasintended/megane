package lol.bai.megane.module.extragenerators;

import io.github.lucaargolo.extragenerators.common.blockentity.AbstractGeneratorBlockEntity;
import io.github.lucaargolo.extragenerators.common.blockentity.ColorfulGeneratorBlockEntity;
import io.github.lucaargolo.extragenerators.common.blockentity.FluidGeneratorBlockEntity;
import io.github.lucaargolo.extragenerators.common.blockentity.FluidItemGeneratorBlockEntity;
import io.github.lucaargolo.extragenerators.common.blockentity.ItemGeneratorBlockEntity;
import lol.bai.megane.api.MeganeModule;
import lol.bai.megane.api.registry.CommonRegistrar;
import lol.bai.megane.module.extragenerators.provider.FluidGeneratorProgressProvider;
import lol.bai.megane.module.extragenerators.provider.GeneratorEnergyProvider;
import lol.bai.megane.module.extragenerators.provider.GeneratorFluidProvider;
import lol.bai.megane.module.extragenerators.provider.GeneratorItemProvider;
import lol.bai.megane.module.extragenerators.provider.GeneratorProgressProvider;

@SuppressWarnings({"UnstableApiUsage", "unused"})
public class MeganeExtraGenerators implements MeganeModule {

    @Override
    public void registerCommon(CommonRegistrar registrar) {
        registrar.addEnergy(AbstractGeneratorBlockEntity.class, new GeneratorEnergyProvider());

        registrar.addFluid(FluidGeneratorBlockEntity.class, new GeneratorFluidProvider<>(FluidGeneratorBlockEntity::getFluidInv));
        registrar.addFluid(FluidItemGeneratorBlockEntity.class, new GeneratorFluidProvider<>(FluidItemGeneratorBlockEntity::getFluidInv));

        registrar.addItem(ColorfulGeneratorBlockEntity.class, new GeneratorItemProvider<>(ColorfulGeneratorBlockEntity::getItemInv));
        registrar.addItem(FluidGeneratorBlockEntity.class, new GeneratorItemProvider<>(FluidGeneratorBlockEntity::getItemInv));
        registrar.addItem(FluidItemGeneratorBlockEntity.class, new GeneratorItemProvider<>(FluidItemGeneratorBlockEntity::getItemInv));
        registrar.addItem(ItemGeneratorBlockEntity.class, new GeneratorItemProvider<>(ItemGeneratorBlockEntity::getItemInv));

        registrar.addProgress(ItemGeneratorBlockEntity.class, new GeneratorProgressProvider<>(
            ItemGeneratorBlockEntity::getItemInv, ItemGeneratorBlockEntity::getBurningFuel,
            0));

        registrar.addProgress(ColorfulGeneratorBlockEntity.class, new GeneratorProgressProvider<>(
            ColorfulGeneratorBlockEntity::getItemInv, ColorfulGeneratorBlockEntity::getBurningFuel,
            0, 1, 2));

        registrar.addProgress(FluidGeneratorBlockEntity.class, new FluidGeneratorProgressProvider<>(
            FluidGeneratorBlockEntity::getItemInv, FluidGeneratorBlockEntity::getBurningFuel,
            0, 1));

        registrar.addProgress(FluidItemGeneratorBlockEntity.class, new FluidGeneratorProgressProvider<>(
            FluidItemGeneratorBlockEntity::getItemInv, FluidItemGeneratorBlockEntity::getBurningFuel,
            0, 1, 2));
    }

}
