package badasintended.megane.impl;

import badasintended.megane.api.MeganeEntrypoint;
import badasintended.megane.api.provider.InventoryProvider;
import badasintended.megane.impl.mixin.minecraft.AccessorHorseBaseEntity;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.block.entity.JukeboxBlockEntity;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.entity.player.PlayerEntity;

import static badasintended.megane.api.registry.TooltipRegistry.BLOCK_INVENTORY;
import static badasintended.megane.api.registry.TooltipRegistry.ENTITY_INVENTORY;

public class Minecraft implements MeganeEntrypoint {

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

        ENTITY_INVENTORY.register(PlayerEntity.class, InventoryProvider.of(
            t -> t.inventory.size(), (t, i) -> t.inventory.getStack(i)
        ));

        ENTITY_INVENTORY.register(AbstractDonkeyEntity.class, InventoryProvider.of(
            t -> ((AccessorHorseBaseEntity) t).getInvSize() - 1, (t, i) -> ((AccessorHorseBaseEntity) t).getItems().getStack(i + 1)
        ));
    }

}
