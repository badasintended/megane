package badasintended.megane.runtime;

import badasintended.megane.runtime.component.AlignResetComponent;
import badasintended.megane.runtime.component.block.BeaconComponent;
import badasintended.megane.runtime.component.block.BeeHiveComponent;
import badasintended.megane.runtime.component.block.BlockInventoryComponent;
import badasintended.megane.runtime.component.block.CauldronComponent;
import badasintended.megane.runtime.component.block.ComposterComponent;
import badasintended.megane.runtime.component.block.EnergyComponent;
import badasintended.megane.runtime.component.block.FluidComponent;
import badasintended.megane.runtime.component.block.ProgressComponent;
import badasintended.megane.runtime.component.entity.EntityInventoryComponent;
import badasintended.megane.runtime.component.entity.PetOwnerComponent;
import badasintended.megane.runtime.component.entity.PlayerHeadComponent;
import badasintended.megane.runtime.component.entity.SpawnEggComponent;
import badasintended.megane.runtime.component.entity.StatusEffectComponent;
import badasintended.megane.runtime.data.block.BeaconData;
import badasintended.megane.runtime.data.block.BlockInventoryData;
import badasintended.megane.runtime.data.block.EnergyData;
import badasintended.megane.runtime.data.block.FluidData;
import badasintended.megane.runtime.data.block.ProgressData;
import badasintended.megane.runtime.data.entity.EntityInventoryData;
import badasintended.megane.runtime.data.entity.PetOwnerData;
import badasintended.megane.runtime.data.entity.StatusEffectData;
import badasintended.megane.runtime.renderer.AlignedTextRenderer;
import badasintended.megane.runtime.renderer.BarRenderer;
import badasintended.megane.runtime.renderer.InventoryRenderer;
import badasintended.megane.runtime.renderer.ProgressRenderer;
import badasintended.megane.runtime.renderer.StatusEffectRenderer;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import net.minecraft.block.BeaconBlock;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.Block;
import net.minecraft.block.CauldronBlock;
import net.minecraft.block.ComposterBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

import static badasintended.megane.util.MeganeUtils.id;
import static mcp.mobius.waila.api.TooltipPosition.BODY;
import static mcp.mobius.waila.api.TooltipPosition.HEAD;

public class MeganeWaila implements IWailaPlugin {

    public static final Identifier INVENTORY = id("inventory");
    public static final Identifier BAR = id("bar");
    public static final Identifier PROGRESS = id("progress");
    public static final Identifier ALIGNED = id("aligned");
    public static final Identifier EFFECT = id("effect");

    private static final Class<Block> BLOCK = Block.class;
    private static final Class<LivingEntity> ENTITY = LivingEntity.class;

    @Override
    public void register(IRegistrar r) {
        // Renderer
        r.registerTooltipRenderer(INVENTORY, new InventoryRenderer());
        r.registerTooltipRenderer(BAR, new BarRenderer());
        r.registerTooltipRenderer(PROGRESS, new ProgressRenderer());
        r.registerTooltipRenderer(ALIGNED, new AlignedTextRenderer());
        r.registerTooltipRenderer(EFFECT, new StatusEffectRenderer());

        // --- BLOCK ---
        // Component
        r.registerComponentProvider(new AlignResetComponent.Block(), HEAD, BLOCK);
        r.registerComponentProvider(new EnergyComponent(), HEAD, BLOCK);
        r.registerComponentProvider(new FluidComponent(), HEAD, BLOCK);
        r.registerComponentProvider(new CauldronComponent(), HEAD, CauldronBlock.class);
        r.registerComponentProvider(new ComposterComponent(), HEAD, ComposterBlock.class);
        r.registerComponentProvider(new BeeHiveComponent(), HEAD, BeehiveBlock.class);

        r.registerComponentProvider(new BlockInventoryComponent(), BODY, BLOCK);
        r.registerComponentProvider(new ProgressComponent(), BODY, BLOCK);
        r.registerComponentProvider(new BeaconComponent(), BODY, BeaconBlock.class);


        // Server Data
        r.registerBlockDataProvider(new BlockInventoryData(), BLOCK);
        r.registerBlockDataProvider(new EnergyData(), BLOCK);
        r.registerBlockDataProvider(new FluidData(), BLOCK);
        r.registerBlockDataProvider(new ProgressData(), BLOCK);
        r.registerBlockDataProvider(new BeaconData(), BeaconBlock.class);

        // --- ENTITY ---
        r.registerEntityStackProvider(new SpawnEggComponent(), ENTITY);
        r.registerEntityStackProvider(new PlayerHeadComponent(), PlayerEntity.class);

        // Component
        r.registerComponentProvider(new AlignResetComponent.Entity(), HEAD, ENTITY);
        r.registerComponentProvider(new PetOwnerComponent(), HEAD, ENTITY);

        r.registerComponentProvider(new EntityInventoryComponent(), BODY, ENTITY);
        r.registerComponentProvider(new StatusEffectComponent(), BODY, ENTITY);

        // Server Data
        r.registerEntityDataProvider(new EntityInventoryData(), ENTITY);
        r.registerEntityDataProvider(new PetOwnerData(), ENTITY);
        r.registerEntityDataProvider(new StatusEffectData(), ENTITY);
    }

}
