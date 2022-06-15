package lol.bai.megane.module.vanilla;

import lol.bai.megane.api.MeganeModule;
import lol.bai.megane.api.registry.ClientRegistrar;
import lol.bai.megane.api.registry.CommonRegistrar;
import lol.bai.megane.module.vanilla.provider.BeehiveItemProvider;
import lol.bai.megane.module.vanilla.provider.CampfireItemProvider;
import lol.bai.megane.module.vanilla.provider.ChestItemProvider;
import lol.bai.megane.module.vanilla.provider.DonkeyItemProvider;
import lol.bai.megane.module.vanilla.provider.EnderChestItemProvider;
import lol.bai.megane.module.vanilla.provider.FurnaceProgressProvider;
import lol.bai.megane.module.vanilla.provider.HopperItemProvider;
import lol.bai.megane.module.vanilla.provider.JukeboxItemProvider;
import lol.bai.megane.module.vanilla.provider.LavaCauldronProvider;
import lol.bai.megane.module.vanilla.provider.LavaFluidInfoProvider;
import lol.bai.megane.module.vanilla.provider.LockableContainerItemProvider;
import lol.bai.megane.module.vanilla.provider.LootableContainerItemProvider;
import lol.bai.megane.module.vanilla.provider.PlayerItemProvider;
import lol.bai.megane.module.vanilla.provider.WaterCauldronProvider;
import lol.bai.megane.module.vanilla.provider.WaterFluidInfoProvider;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.block.entity.JukeboxBlockEntity;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.fluid.WaterFluid;

public class MeganeVanilla implements MeganeModule {

    @Override
    public void registerCommon(CommonRegistrar registrar) {
        registrar.addCauldron(Blocks.WATER_CAULDRON, new WaterCauldronProvider());
        registrar.addCauldron(Blocks.LAVA_CAULDRON, new LavaCauldronProvider());

        registrar.addItem(JukeboxBlockEntity.class, new JukeboxItemProvider());
        registrar.addItem(CampfireBlockEntity.class, new CampfireItemProvider());
        registrar.addItem(BeehiveBlockEntity.class, new BeehiveItemProvider());
        registrar.addItem(ChestBlockEntity.class, new ChestItemProvider());
        registrar.addItem(EnderChestBlockEntity.class, new EnderChestItemProvider());

        registrar.addItem(1100, LootableContainerBlockEntity.class, new LootableContainerItemProvider<>());
        registrar.addItem(1200, LockableContainerBlockEntity.class, new LockableContainerItemProvider<>());
        registrar.addItem(1300, BlockEntity.class, new HopperItemProvider());

        registrar.addItem(PlayerEntity.class, new PlayerItemProvider());
        registrar.addItem(AbstractDonkeyEntity.class, new DonkeyItemProvider());

        registrar.addProgress(AbstractFurnaceBlockEntity.class, new FurnaceProgressProvider());
    }

    @Override
    public void registerClient(ClientRegistrar registrar) {
        registrar.addFluidInfo(WaterFluid.class, new WaterFluidInfoProvider());
        registrar.addFluidInfo(LavaFluid.class, new LavaFluidInfoProvider());
    }

}
