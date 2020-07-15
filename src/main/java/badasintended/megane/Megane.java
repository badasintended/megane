package badasintended.megane;

import badasintended.megane.provider.EffectiveToolProvider;
import badasintended.megane.provider.EnergyProvider;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.TooltipPosition;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;

public class Megane implements IWailaPlugin {

    public static final String ID = "megane";

    public static Identifier id(String path) {
        return new Identifier(ID, path);
    }

    @Override
    public void register(IRegistrar r) {
        r.addConfig(Config.EFFECTIVE_TOOL, true);
        r.addConfig(Config.ENERGY_TR, true);

        r.registerComponentProvider(EnergyProvider.INSTANCE, TooltipPosition.BODY, Block.class);
        r.registerBlockDataProvider(EnergyProvider.INSTANCE, Block.class);

        r.registerComponentProvider(EffectiveToolProvider.INSTANCE, TooltipPosition.BODY, Block.class);
    }

    public static class Config {
        public static final Identifier EFFECTIVE_TOOL = id("block/tool");
        public static final Identifier ENERGY_TR = id("energy/techreborn");
    }

}
