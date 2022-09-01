package lol.bai.megane.module.create.provider;

import com.simibubi.create.content.contraptions.components.deployer.DeployerTileEntity;
import lol.bai.megane.api.provider.ItemProvider;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class DeployerItemProvider extends ItemProvider<DeployerTileEntity> {

    @Override
    public int getSlotCount() {
        return 1;
    }

    @Override
    public @NotNull ItemStack getStack(int slot) {
        return getObject().getPlayer().getMainHandStack();
    }

}
