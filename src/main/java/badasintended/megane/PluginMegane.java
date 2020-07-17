package badasintended.megane;

import badasintended.megane.provider.component.EffectiveToolComponent;
import badasintended.megane.provider.component.EnergyComponent;
import badasintended.megane.provider.component.InventoryComponent;
import badasintended.megane.provider.data.InventoryData;
import badasintended.megane.provider.data.RegisteredEnergyData;
import badasintended.megane.provider.data.TeamRebornEnergyData;
import badasintended.megane.renderer.InventoryRenderer;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.TooltipPosition;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;

public class PluginMegane implements IWailaPlugin {

    public static final String ID = "waila.megane";

    public static Identifier id(String path) {
        return new Identifier(ID, path);
    }

    public static boolean hasMod(String id) {
        return FabricLoader.getInstance().isModLoaded(id);
    }

    @Override
    public void register(IRegistrar r) {
        // Config
        r.addConfig(Config.ENTITY_ICON, true);
        r.addConfig(Config.EFFECTIVE_TOOL, true);
        r.addConfig(Config.INVENTORY, true);
        r.addConfig(Config.ENERGY, true);

        // Renderer
        r.registerTooltipRenderer(Render.INVENTORY, InventoryRenderer.INSTANCE);

        // Component
        r.registerComponentProvider(EnergyComponent.INSTANCE, TooltipPosition.BODY, Block.class);
        r.registerComponentProvider(EffectiveToolComponent.INSTANCE, TooltipPosition.BODY, Block.class);
        r.registerComponentProvider(InventoryComponent.INSTANCE, TooltipPosition.BODY, Block.class);

        // Server Data
        r.registerBlockDataProvider(InventoryData.INSTANCE, Block.class);
        r.registerBlockDataProvider(RegisteredEnergyData.INSTANCE, Block.class);

        // Conditional Server Data
        if (hasMod("team_reborn_energy")) r.registerBlockDataProvider(TeamRebornEnergyData.INSTANCE, Block.class);
    }

    public static class Config {
        public static final Identifier ENTITY_ICON = id("entity_icon");
        public static final Identifier INVENTORY = id("inventory");
        public static final Identifier EFFECTIVE_TOOL = id("effective_tool");
        public static final Identifier ENERGY = id("energy");

        private static Identifier id(String path) {
            return new Identifier("megane", path);
        }
    }

    public static class Render {
        public static final Identifier ENTITY_ICON = id("entity_icon");
        public static final Identifier INVENTORY = id("inventory_renderer");
    }

}
