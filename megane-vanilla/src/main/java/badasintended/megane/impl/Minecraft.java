package badasintended.megane.impl;

import badasintended.megane.api.MeganeModule;
import badasintended.megane.api.provider.FluidInfoProvider;
import badasintended.megane.api.provider.InventoryProvider;
import badasintended.megane.api.registry.MeganeClientRegistrar;
import badasintended.megane.api.registry.MeganeRegistrar;
import badasintended.megane.impl.mixin.minecraft.AHorseBaseEntity;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.block.entity.JukeboxBlockEntity;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class Minecraft implements MeganeModule {

    private static final Text WATER_NAME = new TranslatableText("block.minecraft.water");
    private static final Text LAVA_NAME = new TranslatableText("block.minecraft.lava");

    @Override
    public void register(MeganeRegistrar registrar) {
        registrar
            .inventory(JukeboxBlockEntity.class, InventoryProvider.of(
                t -> 1, (t, i) -> t.getRecord()
            ))

            .inventory(CampfireBlockEntity.class, InventoryProvider.of(
                t -> t.getItemsBeingCooked().size(), (t, i) -> t.getItemsBeingCooked().get(i)
            ))

            .inventory(BeehiveBlockEntity.class, InventoryProvider.of(
                t -> 1, (t, i) -> new ItemStack(Items.HONEYCOMB, t.getBeeCount())
            ))

            .inventory(PlayerEntity.class, InventoryProvider.of(
                t -> t.inventory.size(), (t, i) -> t.inventory.getStack(i)
            ))

            .inventory(AbstractDonkeyEntity.class, InventoryProvider.of(
                t -> ((AHorseBaseEntity) t).getInvSize() - 1, (t, i) -> ((AHorseBaseEntity) t).getItems().getStack(i + 1)
            ))

            .inventory(EnderChestBlockEntity.class, new InventoryProvider<EnderChestBlockEntity>() {
                Inventory enderChest;

                @Override
                public void setupContext(World world, ServerPlayerEntity player) {
                    enderChest = player.getEnderChestInventory();
                }

                @Override
                public boolean hasInventory(EnderChestBlockEntity enderChestBlockEntity) {
                    return true;
                }

                @Override
                public int size(EnderChestBlockEntity enderChestBlockEntity) {
                    return enderChest.size();
                }

                @Override
                public @NotNull ItemStack getStack(EnderChestBlockEntity enderChestBlockEntity, int slot) {
                    return enderChest.getStack(slot);
                }
            })

            .inventory(BlockEntity.class, new InventoryProvider<BlockEntity>() {
                World world;
                Inventory inventory;

                @Override
                public void setupContext(World world, ServerPlayerEntity player) {
                    this.world = world;
                }

                @Override
                public boolean hasInventory(BlockEntity blockEntity) {
                    inventory = HopperBlockEntity.getInventoryAt(world, blockEntity.getPos());
                    return inventory != null;
                }

                @Override
                public int size(BlockEntity blockEntity) {
                    return inventory.size();
                }

                @Override
                public @NotNull ItemStack getStack(BlockEntity blockEntity, int slot) {
                    return inventory.getStack(slot);
                }
            });
    }

    @Override
    public void registerClient(MeganeClientRegistrar registrar) {
        registrar
            .fluid(WaterFluid.class, FluidInfoProvider.of((f, b) -> b.getWaterColor(), f -> WATER_NAME))
            .fluid(LavaFluid.class, FluidInfoProvider.of(0xd45a12, LAVA_NAME));
    }

}
