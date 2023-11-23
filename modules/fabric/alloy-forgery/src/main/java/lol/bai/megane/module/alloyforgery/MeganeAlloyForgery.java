package lol.bai.megane.module.alloyforgery;

import lol.bai.megane.module.alloyforgery.provider.ForgeControllerProvider;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.data.EnergyData;
import net.minecraft.network.chat.Component;
import wraith.alloyforgery.block.ForgeControllerBlockEntity;

public class MeganeAlloyForgery implements IWailaPlugin {

    @Override
    public void register(IRegistrar registrar) {
        EnergyData.describe("alloy_forgery").color(0xEF5252).unit("").name(Component.translatable("megane.alloy_forgery.fuel"));

        registrar.addBlockData(new ForgeControllerProvider(), ForgeControllerBlockEntity.class);
    }

}
