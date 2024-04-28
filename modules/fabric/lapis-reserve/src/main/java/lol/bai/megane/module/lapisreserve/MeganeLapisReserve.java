package lol.bai.megane.module.lapisreserve;

import lol.bai.megane.module.lapisreserve.provider.LapisReserveProvider;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import net.minecraft.world.level.block.entity.EnchantmentTableBlockEntity;

public class MeganeLapisReserve implements IWailaPlugin {

    @Override
    public void register(IRegistrar registrar) {
        registrar.addBlockData(new LapisReserveProvider(), EnchantmentTableBlockEntity.class);
    }

}
