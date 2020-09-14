package badasintended.megane;

import badasintended.megane.api.MeganeEntrypoint;
import badasintended.megane.tooltip.component.*;
import badasintended.megane.tooltip.data.*;
import badasintended.megane.tooltip.renderer.BarRenderer;
import badasintended.megane.tooltip.renderer.InventoryRenderer;
import badasintended.megane.tooltip.renderer.ProgressRenderer;
import badasintended.megane.util.MeganeUtils;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.CauldronBlock;
import net.minecraft.block.ComposterBlock;
import net.minecraft.util.Identifier;

import java.util.Arrays;

import static badasintended.megane.util.MeganeUtils.*;
import static mcp.mobius.waila.api.TooltipPosition.BODY;

public class Megane implements IWailaPlugin, ModInitializer {

    public static final Identifier INVENTORY = id("inventory");
    public static final Identifier BAR = id("bar");
    public static final Identifier PROGRESS = id("progress");

    private static final Class<Block> BLOCK = Block.class;

    @Override
    public void register(IRegistrar r) {
        // Renderer
        r.registerTooltipRenderer(INVENTORY, InventoryRenderer.INSTANCE);
        r.registerTooltipRenderer(BAR, BarRenderer.INSTANCE);
        r.registerTooltipRenderer(PROGRESS, ProgressRenderer.INSTANCE);

        // Component
        r.registerComponentProvider(BarResetComponent.INSTANCE, BODY, BLOCK);
        r.registerComponentProvider(EnergyComponent.INSTANCE, BODY, BLOCK);
        r.registerComponentProvider(FluidComponent.INSTANCE, BODY, BLOCK);
        r.registerComponentProvider(InventoryComponent.INSTANCE, BODY, BLOCK);
        r.registerComponentProvider(ProgressComponent.INSTANCE, BODY, BLOCK);

        r.registerComponentProvider(CauldronComponent.INSTANCE, BODY, CauldronBlock.class);
        r.registerComponentProvider(ComposterComponent.INSTANCE, BODY, ComposterBlock.class);

        // Server Data
        r.registerBlockDataProvider(InventoryData.INSTANCE, BLOCK);

        r.registerBlockDataProvider(EnergyData.INSTANCE, BLOCK);
        if (hasMod("team_reborn_energy")) r.registerBlockDataProvider(TrEnergyData.INSTANCE, BLOCK);

        r.registerBlockDataProvider(FluidData.INSTANCE, BLOCK);
        if (hasMod("libblockattributes_fluids")) r.registerBlockDataProvider(LbaFluidData.INSTANCE, BLOCK);

        r.registerBlockDataProvider(ProgressData.INSTANCE, BLOCK);
    }

    @Override
    public void onInitialize() {
        FabricLoader.getInstance().getEntrypointContainers("megane", MeganeEntrypoint.class).forEach(val -> {
            MeganeEntrypoint entry = val.getEntrypoint();
            boolean satisfied = entry.dependencies().length == 0 || Arrays.stream(entry.dependencies()).allMatch(MeganeUtils::hasMod);
            if (satisfied) entry.initialize();
            LOGGER.info("[megane] {}abling module {} from {}", satisfied ? "En" : "Dis", entry.getClass().getName(), val.getProvider().getMetadata().getId());
        });
    }

}
