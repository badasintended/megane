package badasintended.megane.runtime;

import badasintended.megane.runtime.component.AlignResetComponent;
import badasintended.megane.runtime.component.block.*;
import badasintended.megane.runtime.component.entity.*;
import badasintended.megane.runtime.data.block.*;
import badasintended.megane.runtime.data.entity.*;
import badasintended.megane.runtime.renderer.*;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

import static badasintended.megane.util.MeganeUtils.id;
import static mcp.mobius.waila.api.TooltipPosition.BODY;

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
        r.registerComponentProvider(new AlignResetComponent.Block(), BODY, BLOCK);
        r.registerComponentProvider(new EnergyComponent(), BODY, BLOCK);
        r.registerComponentProvider(new FluidComponent(), BODY, BLOCK);
        r.registerComponentProvider(new BeaconComponent(), BODY, BeaconBlock.class);
        r.registerComponentProvider(new CauldronComponent(), BODY, CauldronBlock.class);
        r.registerComponentProvider(new ComposterComponent(), BODY, ComposterBlock.class);

        r.registerComponentProvider(new BlockInventoryComponent(), BODY, BLOCK);
        r.registerComponentProvider(new ProgressComponent(), BODY, BLOCK);

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
        r.registerComponentProvider(new AlignResetComponent.Entity(), BODY, ENTITY);
        r.registerComponentProvider(new PetOwnerComponent(), BODY, ENTITY);

        r.registerComponentProvider(new EntityInventoryComponent(), BODY, ENTITY);
        r.registerComponentProvider(new StatusEffectComponent(), BODY, ENTITY);

        // Server Data
        r.registerEntityDataProvider(new EntityInventoryData(), ENTITY);
        r.registerEntityDataProvider(new PetOwnerData(), ENTITY);
        r.registerEntityDataProvider(new StatusEffectData(), ENTITY);
    }

}
