package lol.bai.megane.module.test;

import lol.bai.megane.api.MeganeModule;
import lol.bai.megane.api.registry.ClientRegistrar;
import lol.bai.megane.api.registry.CommonRegistrar;
import lol.bai.megane.module.test.provider.TestEnergyInfoProvider;
import lol.bai.megane.module.test.provider.TestEnergyProvider;
import lol.bai.megane.module.test.provider.TestFluidProvider;
import lol.bai.megane.module.test.provider.TestItemProvider;
import lol.bai.megane.module.test.provider.TestProgressProvider;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.TrappedChestBlockEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.text.LiteralText;

@SuppressWarnings("unused")
public class MeganeTest implements MeganeModule {

    @Override
    public void registerCommon(CommonRegistrar registrar) {
        registrar.addEnergy(ChestBlockEntity.class, new TestEnergyProvider<>());
        registrar.addEnergy(TrappedChestBlockEntity.class, new TestEnergyProvider<>());
        registrar.addFluid(ChestBlockEntity.class, new TestFluidProvider());
        registrar.addItem(ChestBlockEntity.class, new TestItemProvider());
        registrar.addProgress(ChestBlockEntity.class, new TestProgressProvider());
    }

    @Override
    public void registerClient(ClientRegistrar registrar) {
        registrar.addFluidInfo(Fluids.WATER, 0x770000, new LiteralText("TestWater"));
        registrar.addFluidInfo(Fluids.LAVA, 0x007700, new LiteralText("TestLava"));
        registrar.addEnergyInfo("minecraft", 0x000077, "TEST", new LiteralText("TestEnergy"));
        registrar.addEnergyInfo(TrappedChestBlockEntity.class, new TestEnergyInfoProvider());
    }

}
