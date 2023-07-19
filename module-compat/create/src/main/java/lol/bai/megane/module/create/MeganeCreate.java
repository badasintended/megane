package lol.bai.megane.module.create;

import com.simibubi.create.content.fluids.tank.CreativeFluidTankBlockEntity;
import com.simibubi.create.content.fluids.tank.FluidTankBlockEntity;
import com.simibubi.create.content.kinetics.base.BlockBreakingKineticBlockEntity;
import com.simibubi.create.content.kinetics.crusher.CrushingWheelControllerBlockEntity;
import com.simibubi.create.content.kinetics.deployer.DeployerBlockEntity;
import com.simibubi.create.content.kinetics.millstone.MillstoneBlockEntity;
import com.simibubi.create.content.kinetics.mixer.MechanicalMixerBlockEntity;
import com.simibubi.create.content.logistics.vault.ItemVaultBlockEntity;
import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.content.schematics.cannon.SchematicannonBlockEntity;
import com.simibubi.create.content.schematics.table.SchematicTableBlockEntity;
import lol.bai.megane.api.MeganeModule;
import lol.bai.megane.api.registry.ClientRegistrar;
import lol.bai.megane.api.registry.CommonRegistrar;
import lol.bai.megane.module.create.provider.BasinFluidProvider;
import lol.bai.megane.module.create.provider.BasinItemProvider;
import lol.bai.megane.module.create.provider.BasinProgressProvider;
import lol.bai.megane.module.create.provider.BlockBreakingProgressProvider;
import lol.bai.megane.module.create.provider.CreativeFluidTankFluidProvider;
import lol.bai.megane.module.create.provider.CrushingWheelControllerProgressProvider;
import lol.bai.megane.module.create.provider.DeployerItemProvider;
import lol.bai.megane.module.create.provider.FluidTankFluidProvider;
import lol.bai.megane.module.create.provider.ItemStackHandlerItemProvider;
import lol.bai.megane.module.create.provider.MechanicalMixerProgressProvider;
import lol.bai.megane.module.create.provider.MillstoneItemProvider;
import lol.bai.megane.module.create.provider.MillstoneProgressProvider;
import lol.bai.megane.module.create.provider.SchematicannonEnergyInfoProvider;
import lol.bai.megane.module.create.provider.SchematicannonEnergyProvider;
import lol.bai.megane.module.create.provider.SchematicannonProgressProvider;

@SuppressWarnings("unused")
public class MeganeCreate implements MeganeModule {

    @Override
    public void registerCommon(CommonRegistrar registrar) {
        registrar.addFluid(999, CreativeFluidTankBlockEntity.class, new CreativeFluidTankFluidProvider());
        registrar.addFluid(BasinBlockEntity.class, new BasinFluidProvider());
        registrar.addFluid(FluidTankBlockEntity.class, new FluidTankFluidProvider<>());

        registrar.addEnergy(SchematicannonBlockEntity.class, new SchematicannonEnergyProvider());

        registrar.addProgress(BasinBlockEntity.class, new BasinProgressProvider());
        registrar.addProgress(BlockBreakingKineticBlockEntity.class, new BlockBreakingProgressProvider());
        registrar.addProgress(CrushingWheelControllerBlockEntity.class, new CrushingWheelControllerProgressProvider());
        registrar.addProgress(MechanicalMixerBlockEntity.class, new MechanicalMixerProgressProvider());
        registrar.addProgress(MillstoneBlockEntity.class, new MillstoneProgressProvider());
        registrar.addProgress(SchematicannonBlockEntity.class, new SchematicannonProgressProvider());

        registrar.addItem(BasinBlockEntity.class, new BasinItemProvider());
        registrar.addItem(DeployerBlockEntity.class, new DeployerItemProvider());
        registrar.addItem(ItemVaultBlockEntity.class, new ItemStackHandlerItemProvider.Single<>(ItemVaultBlockEntity::getInventoryOfBlock));
        registrar.addItem(MillstoneBlockEntity.class, new MillstoneItemProvider());
        registrar.addItem(SchematicannonBlockEntity.class, new ItemStackHandlerItemProvider.Single<>(t -> t.inventory));
        registrar.addItem(SchematicTableBlockEntity.class, new ItemStackHandlerItemProvider.Single<>(t -> t.inventory));
    }

    @Override
    public void registerClient(ClientRegistrar registrar) {
        registrar.addEnergyInfo(SchematicannonBlockEntity.class, new SchematicannonEnergyInfoProvider());
    }

}
