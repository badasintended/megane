package lol.bai.megane.module.create;

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
import lol.bai.megane.module.create.provider.BasinProvider;
import lol.bai.megane.module.create.provider.BlockBreakingKineticProvider;
import lol.bai.megane.module.create.provider.CrushingWheelControllerProvider;
import lol.bai.megane.module.create.provider.DeployerProvider;
import lol.bai.megane.module.create.provider.FluidTankProvider;
import lol.bai.megane.module.create.provider.ItemVaultProvider;
import lol.bai.megane.module.create.provider.MechanicalMixerProvider;
import lol.bai.megane.module.create.provider.MillstoneProvider;
import lol.bai.megane.module.create.provider.SchematicTableProvider;
import lol.bai.megane.module.create.provider.SchematicannonProvider;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.TooltipPosition;

public class MeganeCreate implements IWailaPlugin {

    @Override
    public void register(IRegistrar registrar) {
        registrar.addBlockData(new BasinProvider(), BasinBlockEntity.class);
        registrar.addBlockData(new BlockBreakingKineticProvider(), BlockBreakingKineticBlockEntity.class);
        registrar.addBlockData(new CrushingWheelControllerProvider(), CrushingWheelControllerBlockEntity.class);
        registrar.addBlockData(new DeployerProvider(), DeployerBlockEntity.class);
        registrar.addBlockData(new FluidTankProvider(), FluidTankBlockEntity.class);
        registrar.addBlockData(new ItemVaultProvider(), ItemVaultBlockEntity.class);
        registrar.addBlockData(new MechanicalMixerProvider(), MechanicalMixerBlockEntity.class);
        registrar.addBlockData(new MillstoneProvider(), MillstoneBlockEntity.class);
        registrar.addBlockData(new SchematicTableProvider(), SchematicTableBlockEntity.class);

        var schematicannonProvider = new SchematicannonProvider();
        registrar.addFeatureConfig(SchematicannonProvider.CONFIG_GUNPOWDER, true);
        registrar.addBlockData(schematicannonProvider, SchematicannonBlockEntity.class);
        registrar.addComponent(schematicannonProvider, TooltipPosition.BODY, SchematicannonBlockEntity.class);
    }

}
