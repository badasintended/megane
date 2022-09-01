package lol.bai.megane.module.create;

import com.simibubi.create.content.contraptions.components.actors.BlockBreakingKineticTileEntity;
import com.simibubi.create.content.contraptions.components.crusher.CrushingWheelControllerTileEntity;
import com.simibubi.create.content.contraptions.components.deployer.DeployerTileEntity;
import com.simibubi.create.content.contraptions.components.millstone.MillstoneTileEntity;
import com.simibubi.create.content.contraptions.components.mixer.MechanicalMixerTileEntity;
import com.simibubi.create.content.contraptions.fluids.tank.CreativeFluidTankTileEntity;
import com.simibubi.create.content.contraptions.fluids.tank.FluidTankTileEntity;
import com.simibubi.create.content.contraptions.processing.BasinTileEntity;
import com.simibubi.create.content.logistics.block.vault.ItemVaultTileEntity;
import com.simibubi.create.content.schematics.block.SchematicTableTileEntity;
import com.simibubi.create.content.schematics.block.SchematicannonTileEntity;
import com.tterrag.registrate.fabric.SimpleFlowableFluid;
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
import lol.bai.megane.module.create.provider.SimpleFlowableFluidInfoProvider;

@SuppressWarnings("unused")
public class MeganeCreate implements MeganeModule {

    @Override
    public void registerCommon(CommonRegistrar registrar) {
        registrar.addFluid(999, CreativeFluidTankTileEntity.class, new CreativeFluidTankFluidProvider());
        registrar.addFluid(BasinTileEntity.class, new BasinFluidProvider());
        registrar.addFluid(FluidTankTileEntity.class, new FluidTankFluidProvider<>());

        registrar.addEnergy(SchematicannonTileEntity.class, new SchematicannonEnergyProvider());

        registrar.addProgress(BasinTileEntity.class, new BasinProgressProvider());
        registrar.addProgress(BlockBreakingKineticTileEntity.class, new BlockBreakingProgressProvider());
        registrar.addProgress(CrushingWheelControllerTileEntity.class, new CrushingWheelControllerProgressProvider());
        registrar.addProgress(MechanicalMixerTileEntity.class, new MechanicalMixerProgressProvider());
        registrar.addProgress(MillstoneTileEntity.class, new MillstoneProgressProvider());
        registrar.addProgress(SchematicannonTileEntity.class, new SchematicannonProgressProvider());

        registrar.addItem(BasinTileEntity.class, new BasinItemProvider());
        registrar.addItem(DeployerTileEntity.class, new DeployerItemProvider());
        registrar.addItem(ItemVaultTileEntity.class, new ItemStackHandlerItemProvider.Single<>(ItemVaultTileEntity::getInventoryOfBlock));
        registrar.addItem(MillstoneTileEntity.class, new MillstoneItemProvider());
        registrar.addItem(SchematicannonTileEntity.class, new ItemStackHandlerItemProvider.Single<>(t -> t.inventory));
        registrar.addItem(SchematicTableTileEntity.class, new ItemStackHandlerItemProvider.Single<>(t -> t.inventory));
    }

    @Override
    public void registerClient(ClientRegistrar registrar) {
        registrar.addFluidInfo(SimpleFlowableFluid.class, new SimpleFlowableFluidInfoProvider());

        registrar.addEnergyInfo(SchematicannonTileEntity.class, new SchematicannonEnergyInfoProvider());
    }

}
