package lol.bai.megane.module.create.provider;

import com.simibubi.create.content.schematics.cannon.SchematicannonBlockEntity;
import mcp.mobius.waila.api.IBlockAccessor;
import mcp.mobius.waila.api.IBlockComponentProvider;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.ITooltip;
import mcp.mobius.waila.api.component.BarComponent;
import mcp.mobius.waila.api.component.PairComponent;
import mcp.mobius.waila.api.component.WrappedComponent;
import mcp.mobius.waila.api.data.ItemData;
import mcp.mobius.waila.api.data.ProgressData;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

public class SchematicannonProvider implements IBlockComponentProvider, IDataProvider<SchematicannonBlockEntity> {

    public static final ResourceLocation CONFIG_GUNPOWDER = new ResourceLocation("megane:create.schematicannon.gunpowder");

    @Override
    public void appendData(IDataWriter data, IServerAccessor<SchematicannonBlockEntity> accessor, IPluginConfig config) {
        data.add(ItemData.class, res -> {
            var inventory = accessor.getTarget().inventory;

            res.add(ItemData.of(config)
                .getter(inventory::getStackInSlot, inventory.getSlotCount()));
        });

        data.add(ProgressData.class, res -> {
            var target = accessor.getTarget();
            var inventory = target.inventory;
            var progressData = ProgressData.ratio((float) target.blocksPlaced / target.blocksToPlace);

            progressData.ensureInputSpace(inventory.getSlotCount());
            for (int i = 0; i < inventory.getSlotCount(); i++) {
                progressData.input(inventory.getStackInSlot(i));
            }

            res.add(progressData);
        });
    }

    @Override
    public void appendBody(ITooltip tooltip, IBlockAccessor accessor, IPluginConfig config) {
        if (!config.getBoolean(CONFIG_GUNPOWDER)) return;

        SchematicannonBlockEntity be = accessor.getBlockEntity();
        if (be == null) return;

        var fuel = be.fuelLevel;
        var text = Component.literal((fuel * 100) + "%");

        tooltip.addLine(new PairComponent(
            new WrappedComponent(Items.GUNPOWDER.getDescription()),
            new BarComponent(fuel, 0xFF2D2D2D, text)));
    }

}
