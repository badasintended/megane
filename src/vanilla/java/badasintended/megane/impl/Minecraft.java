package badasintended.megane.impl;

import badasintended.megane.api.MeganeModule;
import badasintended.megane.api.provider.CauldronFluidProvider;
import badasintended.megane.api.provider.FluidInfoProvider;
import badasintended.megane.api.provider.InventoryProvider;
import badasintended.megane.api.registry.MeganeClientRegistrar;
import badasintended.megane.api.registry.MeganeRegistrar;
import badasintended.megane.impl.mixin.minecraft.AccessorHorseBaseEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.block.entity.JukeboxBlockEntity;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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
            .cauldron(Blocks.WATER_CAULDRON, CauldronFluidProvider.of(
                Fluids.WATER, 1000.0, b -> 1000.0 * b.get(LeveledCauldronBlock.LEVEL) / 3.0
            ))

            .cauldron(Blocks.LAVA_CAULDRON, CauldronFluidProvider.of(
                Fluids.LAVA, 1000.0, b -> 1000.0
            ))

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
                t -> t.getInventory().size(), (t, i) -> t.getInventory().getStack(i)
            ))

            .inventory(AbstractDonkeyEntity.class, InventoryProvider.of(
                t -> ((AccessorHorseBaseEntity) t).getInvSize() - 1, (t, i) -> ((AccessorHorseBaseEntity) t).getItems().getStack(i + 1)
            ))

            .inventory(EnderChestBlockEntity.class, new InventoryProvider<>() {
                Inventory enderChest;

                @Override
                public void setupContext(World world, PlayerEntity player) {
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

            .inventory(BlockEntity.class, new InventoryProvider<>() {
                World world;
                Inventory inventory;

                @Override
                public void setupContext(World world, PlayerEntity player) {
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
            .fluid(WaterFluid.class, FluidInfoProvider.biome((f, b) -> b.getWaterColor(), f -> WATER_NAME))
            .fluid(LavaFluid.class, FluidInfoProvider.of(0xd45a12, LAVA_NAME));
    }

}
