package badasintended.megane.impl;

import badasintended.megane.api.MeganeEntrypoint;
import badasintended.megane.api.registry.ProgressTooltipRegistry;
import badasintended.megane.impl.mixin.fabricfurnaces.AccessorBaseFurnaceEntity;
import badasintended.megane.impl.util.Arrays;
import draylar.fabricfurnaces.entity.BaseFurnaceEntity;
import net.minecraft.inventory.Inventory;

public class FabricFurnaces implements MeganeEntrypoint {

    private static final String[] DEP = new String[]{"fabric-furnaces"};

    @Override
    public String[] dependencies() {
        return DEP;
    }

    @Override
    public void initialize() {
        ProgressTooltipRegistry.register(BaseFurnaceEntity.class, ProgressTooltipRegistry.Provider.of(
            t -> Arrays.A_01, t -> Arrays.A_2, Inventory::getStack,
            t -> {
                double total = t.getCookTime();
                if (total <= 0) return 0;
                return (int) ((double) ((AccessorBaseFurnaceEntity) t).getCookTimeProgress() / total * 100);
            }
        ));
    }

}
