package lol.bai.megane.module.ae2;

import appeng.blockentity.AEBaseInvBlockEntity;
import appeng.blockentity.misc.InscriberBlockEntity;
import appeng.blockentity.misc.VibrationChamberBlockEntity;
import appeng.blockentity.networking.CreativeEnergyCellBlockEntity;
import appeng.blockentity.networking.EnergyCellBlockEntity;
import appeng.blockentity.storage.SkyStoneTankBlockEntity;
import lol.bai.megane.module.ae2.provider.AEBaseInvProvider;
import lol.bai.megane.module.ae2.provider.EnergyCellProvider;
import lol.bai.megane.module.ae2.provider.InscriberProvider;
import lol.bai.megane.module.ae2.provider.SkyStoneTankProvider;
import lol.bai.megane.module.ae2.provider.VibrationChamberProvider;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.data.EnergyData;

public class MeganeAppliedEnergistics2 implements IWailaPlugin {

    @Override
    public void register(IRegistrar registrar) {
        EnergyData.describe("ae2").color(0x64099F).unit("AE");

        registrar.addBlockData(EnergyData.newInfiniteProvider(), CreativeEnergyCellBlockEntity.class);
        registrar.addBlockData(new EnergyCellProvider(), EnergyCellBlockEntity.class);
        registrar.addBlockData(new AEBaseInvProvider(), AEBaseInvBlockEntity.class);
        registrar.addBlockData(new SkyStoneTankProvider(), SkyStoneTankBlockEntity.class);
        registrar.addBlockData(new InscriberProvider(), InscriberBlockEntity.class);
        registrar.addBlockData(new VibrationChamberProvider(), VibrationChamberBlockEntity.class);
    }

}
