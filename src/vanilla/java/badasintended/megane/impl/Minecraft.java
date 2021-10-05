package badasintended.megane.impl;

import badasintended.megane.api.MeganeModule;
import badasintended.megane.api.provider.CauldronFluidProvider;
import badasintended.megane.api.provider.FluidInfoProvider;
import badasintended.megane.api.provider.InventoryProvider;
import badasintended.megane.api.provider.ProgressProvider;
import badasintended.megane.api.registry.MeganeClientRegistrar;
import badasintended.megane.api.registry.MeganeRegistrar;
import badasintended.megane.impl.mixin.minecraft.AccessorAbstractFurnaceBlockEntity;
import badasintended.megane.impl.mixin.minecraft.AccessorHorseBaseEntity;
import badasintended.megane.impl.mixin.minecraft.AccessorLootableContainerBlockEntity;
import badasintended.megane.impl.util.A;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.block.entity.JukeboxBlockEntity;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
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

            .progress(AbstractFurnaceBlockEntity.class, ProgressProvider.of(
                t -> A.A_01, t -> A.A_2, Inventory::getStack,
                t -> {
                    AccessorAbstractFurnaceBlockEntity furnace = (AccessorAbstractFurnaceBlockEntity) t;
                    return (int) ((float) furnace.getCookTime() / (float) furnace.getCookTimeTotal() * 100);
                }
            ))

            .inventory(ChestBlockEntity.class, new InventoryProvider<>() {
                Inventory inventory;

                @Override
                public boolean hasInventory(ChestBlockEntity chestBlockEntity) {
                    BlockState state = chestBlockEntity.getCachedState();
                    if (state.getBlock() instanceof ChestBlock block) {
                        inventory = ChestBlock.getInventory(block, state, chestBlockEntity.getWorld(), chestBlockEntity.getPos(), true);
                        return inventory != null;
                    } else {
                        return false;
                    }
                }

                @Override
                public int size(ChestBlockEntity chestBlockEntity) {
                    return inventory.size();
                }

                @Override
                public @NotNull ItemStack getStack(ChestBlockEntity chestBlockEntity, int slot) {
                    return inventory.getStack(slot);
                }
            })

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

            .inventory(1100, LootableContainerBlockEntity.class, new InventoryProvider<>() {
                private boolean generated;

                @Override
                public boolean hasInventory(LootableContainerBlockEntity lootableContainerBlockEntity) {
                    generated = ((AccessorLootableContainerBlockEntity) lootableContainerBlockEntity).getLootTableId() == null;
                    return true;
                }

                @Override
                public int size(LootableContainerBlockEntity lootableContainerBlockEntity) {
                    return generated ? lootableContainerBlockEntity.size() : 0;
                }

                @Override
                public @NotNull ItemStack getStack(LootableContainerBlockEntity lootableContainerBlockEntity, int slot) {
                    return lootableContainerBlockEntity.getStack(slot);
                }
            })

            .inventory(1200, LockableContainerBlockEntity.class, new InventoryProvider<>() {
                private PlayerEntity player;
                private boolean unlocked;

                @Override
                public void setupContext(World world, PlayerEntity player) {
                    this.player = player;
                }

                @Override
                public boolean hasInventory(LockableContainerBlockEntity lockableContainerBlockEntity) {
                    unlocked = lockableContainerBlockEntity.checkUnlocked(player);
                    return true;
                }

                @Override
                public int size(LockableContainerBlockEntity lockableContainerBlockEntity) {
                    return unlocked ? lockableContainerBlockEntity.size() : 0;
                }

                @Override
                public @NotNull ItemStack getStack(LockableContainerBlockEntity lockableContainerBlockEntity, int slot) {
                    return lockableContainerBlockEntity.getStack(slot);
                }
            })

            .inventory(1300, BlockEntity.class, new InventoryProvider<>() {
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
