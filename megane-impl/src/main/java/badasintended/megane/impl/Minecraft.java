package badasintended.megane.impl;

import badasintended.megane.api.MeganeModule;
import badasintended.megane.api.provider.FluidInfoProvider;
import badasintended.megane.api.provider.InventoryProvider;
import badasintended.megane.impl.mixin.minecraft.AHorseBaseEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.block.entity.JukeboxBlockEntity;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import static badasintended.megane.api.registry.TooltipRegistry.BLOCK_INVENTORY;
import static badasintended.megane.api.registry.TooltipRegistry.ENTITY_INVENTORY;
import static badasintended.megane.api.registry.TooltipRegistry.FLUID_INFO;

public class Minecraft implements MeganeModule {

    private static final Text WATER_NAME = new TranslatableText("block.minecraft.water");
    private static final Text LAVA_NAME = new TranslatableText("block.minecraft.lava");

    @Override
    public void initialize() {
        BLOCK_INVENTORY.register(JukeboxBlockEntity.class, InventoryProvider.of(
            t -> 1, (t, i) -> t.getRecord()
        ));

        BLOCK_INVENTORY.register(JukeboxBlockEntity.class, InventoryProvider.of(
            t -> 1, (t, i) -> t.getRecord()
        ));

        BLOCK_INVENTORY.register(CampfireBlockEntity.class, InventoryProvider.of(
            t -> t.getItemsBeingCooked().size(), (t, i) -> t.getItemsBeingCooked().get(i)
        ));

        BLOCK_INVENTORY.register(BeehiveBlockEntity.class, InventoryProvider.of(
            t -> 1, (t, i) -> new ItemStack(Items.HONEYCOMB, t.getBeeCount())
        ));

        ENTITY_INVENTORY.register(PlayerEntity.class, InventoryProvider.of(
            t -> t.inventory.size(), (t, i) -> t.inventory.getStack(i)
        ));

        ENTITY_INVENTORY.register(AbstractDonkeyEntity.class, InventoryProvider.of(
            t -> ((AHorseBaseEntity) t).getInvSize() - 1, (t, i) -> ((AHorseBaseEntity) t).getItems().getStack(i + 1)
        ));
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void initializeClient() {
        FLUID_INFO.register(WaterFluid.class, FluidInfoProvider.of((f, b) -> b.getWaterColor(), f -> WATER_NAME));
        FLUID_INFO.register(LavaFluid.class, FluidInfoProvider.of(0xd45a12, LAVA_NAME));
    }

}
