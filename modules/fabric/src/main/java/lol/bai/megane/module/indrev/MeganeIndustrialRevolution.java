package lol.bai.megane.module.indrev;

import lol.bai.megane.module.indrev.provider.CraftingMachineProvider;
import lol.bai.megane.module.indrev.provider.FluidComponentHolderProvider;
import lol.bai.megane.module.indrev.provider.MachineProvider;
import lol.bai.megane.module.indrev.provider.ModularWorkbenchProvider;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.data.EnergyData;
import me.steven.indrev.blockentities.MachineBlockEntity;
import me.steven.indrev.blockentities.crafters.CraftingMachineBlockEntity;
import me.steven.indrev.blockentities.modularworkbench.ModularWorkbenchBlockEntity;
import me.steven.indrev.blockentities.storage.TankBlockEntity;

@SuppressWarnings("unused")
public class MeganeIndustrialRevolution implements IWailaPlugin {

    @Override
    public void register(IRegistrar registrar) {
        EnergyData.describe("indrev").color(0x3B4ADE).unit("LF");

        registrar.addBlockData(new MachineProvider(), MachineBlockEntity.class);

        registrar.addBlockData(new FluidComponentHolderProvider<>(TankBlockEntity::getFluidComponent), TankBlockEntity.class);
        registrar.addBlockData(new FluidComponentHolderProvider<MachineBlockEntity<?>>(MachineBlockEntity::getFluidComponent), MachineBlockEntity.class);

        registrar.addBlockData(new CraftingMachineProvider(), CraftingMachineBlockEntity.class);
        registrar.addBlockData(new ModularWorkbenchProvider(), ModularWorkbenchBlockEntity.class);
    }

}
