package badasintended.megane.impl;

import badasintended.megane.api.MeganeEntrypoint;
import badasintended.megane.api.provider.ProgressProvider;
import badasintended.megane.impl.mixin.fabricfurnaces.AccessorBaseFurnaceEntity;
import badasintended.megane.impl.util.A;
import draylar.fabricfurnaces.entity.BaseFurnaceEntity;
import net.minecraft.inventory.Inventory;

import static badasintended.megane.api.registry.TooltipRegistry.PROGRESS;


public class FabricFurnaces implements MeganeEntrypoint {

    private static final String[] DEP = new String[]{"fabric-furnaces"};

    @Override
    public String[] dependencies() {
        return DEP;
    }

    @Override
    public void initialize() {
        PROGRESS.register(BaseFurnaceEntity.class, ProgressProvider.of(
            t -> A.A_01, t -> A.A_2, Inventory::getStack,
            t -> {
                double total = t.getCookTime();
                if (total <= 0) return 0;
                return (int) ((double) ((AccessorBaseFurnaceEntity) t).getCookTimeProgress() / total * 100);
            }
        ));
    }

}
