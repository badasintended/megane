package badasintended.megane;

import badasintended.megane.api.MeganeApi;
import badasintended.megane.provider.component.*;
import badasintended.megane.provider.data.*;
import badasintended.megane.renderer.BarRenderer;
import badasintended.megane.renderer.InventoryRenderer;
import badasintended.megane.renderer.ProgressRenderer;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.TooltipPosition;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.CauldronBlock;
import net.minecraft.block.ComposterBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

import static badasintended.megane.MeganeUtils.*;

public class Megane implements IWailaPlugin, ModInitializer {

    public static final Identifier INVENTORY = id("inventory");
    public static final Identifier EFFECTIVE_TOOL = id("effective_tool");
    public static final Identifier ENERGY = id("energy");
    public static final Identifier BAR = id("bar");
    public static final Identifier FLUID = id("fluid");
    public static final Identifier ENTITY_INFO = id("armor");
    public static final Identifier PROGRESS = id("progress");

    @Override
    public void register(IRegistrar r) {
        // Config
        r.addConfig(EFFECTIVE_TOOL, true);
        r.addConfig(INVENTORY, true);
        r.addConfig(PROGRESS, true);
        r.addConfig(ENERGY, true);
        r.addConfig(FLUID, true);
        r.addConfig(ENTITY_INFO, true);

        // Renderer
        r.registerTooltipRenderer(INVENTORY, InventoryRenderer.INSTANCE);
        r.registerTooltipRenderer(BAR, BarRenderer.INSTANCE);
        r.registerTooltipRenderer(PROGRESS, ProgressRenderer.INSTANCE);

        // Component
        r.registerComponentProvider(BarResetComponent.INSTANCE, TooltipPosition.BODY, Block.class);
        r.registerComponentProvider(EffectiveToolComponent.INSTANCE, TooltipPosition.BODY, Block.class);
        r.registerComponentProvider(EnergyComponent.INSTANCE, TooltipPosition.BODY, Block.class);
        r.registerComponentProvider(FluidComponent.INSTANCE, TooltipPosition.BODY, Block.class);
        r.registerComponentProvider(InventoryComponent.INSTANCE, TooltipPosition.BODY, Block.class);
        r.registerComponentProvider(ProgressComponent.INSTANCE, TooltipPosition.BODY, Block.class);

        r.registerComponentProvider(CauldronComponent.INSTANCE, TooltipPosition.BODY, CauldronBlock.class);
        r.registerComponentProvider(ComposterComponent.INSTANCE, TooltipPosition.BODY, ComposterBlock.class);

        r.registerComponentProvider(EntityInfoComponent.INSTANCE, TooltipPosition.BODY, LivingEntity.class);

        // Server Data
        r.registerBlockDataProvider(InventoryData.INSTANCE, Block.class);
        r.registerBlockDataProvider(RegisteredEnergyData.INSTANCE, Block.class);
        r.registerBlockDataProvider(FluidData.INSTANCE, Block.class);
        r.registerBlockDataProvider(ProgressData.INSTANCE, Block.class);

        r.registerEntityDataProvider(EntityInfoData.INSTANCE, LivingEntity.class);

        // Conditional Server Data
        if (hasMod("team_reborn_energy")) r.registerBlockDataProvider(TeamRebornEnergyData.INSTANCE, Block.class);
    }

    @Override
    public void onInitialize() {
        FabricLoader.getInstance().getEntrypointContainers("megane", MeganeApi.class).forEach(val -> {
            MeganeApi entry = val.getEntrypoint();
            boolean satisfied = entry.modDependencies().length == 0;
            for (String modId : entry.modDependencies()) {
                satisfied = hasMod(modId);
            }
            if (satisfied) entry.initialize();
            LOGGER.info(String.format("%sabling module %s", satisfied ? "En" : "Dis", entry.getClass().getName()));
        });
    }

}
