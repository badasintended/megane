package badasintended.megane;

import badasintended.megane.provider.component.EffectiveToolComponent;
import badasintended.megane.provider.component.EnergyComponent;
import badasintended.megane.provider.component.FluidComponent;
import badasintended.megane.provider.component.InventoryComponent;
import badasintended.megane.provider.data.InventoryData;
import badasintended.megane.provider.data.RegisteredEnergyData;
import badasintended.megane.provider.data.RegisteredFluidData;
import badasintended.megane.provider.data.TeamRebornEnergyData;
import badasintended.megane.renderer.BarRenderer;
import badasintended.megane.renderer.InventoryRenderer;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.TooltipPosition;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;

import static badasintended.megane.Utils.hasMod;
import static badasintended.megane.Utils.id;

public class PluginMegane implements IWailaPlugin {

    public static final Identifier INVENTORY = id("inventory");
    public static final Identifier EFFECTIVE_TOOL = id("effective_tool");
    public static final Identifier ENERGY = id("energy");
    public static final Identifier BAR = id("bar");
    public static final Identifier FLUID = id("fluid");

    @Override
    public void register(IRegistrar r) {
        // Config
        r.addConfig(EFFECTIVE_TOOL, true);
        r.addConfig(INVENTORY, true);
        r.addConfig(ENERGY, true);
        r.addConfig(FLUID, true);

        // Renderer
        r.registerTooltipRenderer(INVENTORY, InventoryRenderer.INSTANCE);
        r.registerTooltipRenderer(BAR, BarRenderer.INSTANCE);

        // Component
        r.registerComponentProvider(EnergyComponent.INSTANCE, TooltipPosition.BODY, Block.class);
        r.registerComponentProvider(FluidComponent.INSTANCE, TooltipPosition.BODY, Block.class);
        r.registerComponentProvider(EffectiveToolComponent.INSTANCE, TooltipPosition.BODY, Block.class);
        r.registerComponentProvider(InventoryComponent.INSTANCE, TooltipPosition.BODY, Block.class);

        // Server Data
        r.registerBlockDataProvider(InventoryData.INSTANCE, Block.class);
        r.registerBlockDataProvider(RegisteredEnergyData.INSTANCE, Block.class);
        r.registerBlockDataProvider(RegisteredFluidData.INSTANCE, Block.class);

        // Conditional Server Data
        if (hasMod("team_reborn_energy")) r.registerBlockDataProvider(TeamRebornEnergyData.INSTANCE, Block.class);
    }

}
