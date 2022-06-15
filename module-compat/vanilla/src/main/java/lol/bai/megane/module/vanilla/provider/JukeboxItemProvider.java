package lol.bai.megane.module.vanilla.provider;

import lol.bai.megane.api.provider.ItemProvider;
import net.minecraft.block.entity.JukeboxBlockEntity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class JukeboxItemProvider extends ItemProvider<JukeboxBlockEntity> {

    @Override
    public int getSlotCount() {
        return 1;
    }

    @Override
    public @NotNull ItemStack getStack(int slot) {
        return getObject().getRecord();
    }

}
