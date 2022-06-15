package lol.bai.megane.module.test.provider;

import lol.bai.megane.api.provider.EnergyInfoProvider;
import net.minecraft.block.entity.TrappedChestBlockEntity;
import org.jetbrains.annotations.Nullable;

public class TestEnergyInfoProvider extends EnergyInfoProvider<TrappedChestBlockEntity> {

    @Override
    public int getColor() {
        return 0x000000;
    }

    @Nullable
    @Override
    public String getUnit() {
        return null;
    }

}
