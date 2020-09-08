package badasintended.megane.module;

import badasintended.megane.MeganeUtils;
import badasintended.megane.api.MeganeEntrypoint;
import badasintended.megane.api.registry.FluidTooltipRegistry;
import badasintended.megane.api.registry.ProgressTooltipRegistry;
import reborncore.api.recipe.IRecipeCrafterProvider;
import reborncore.common.blockentity.MachineBaseBlockEntity;
import techreborn.blockentity.machine.GenericMachineBlockEntity;

public class MeganeTechReborn implements MeganeEntrypoint {

    @Override
    public void initialize() {
        ProgressTooltipRegistry.register(GenericMachineBlockEntity.class, ProgressTooltipRegistry.Provider.of(
            IRecipeCrafterProvider::getInputSlots,
            b -> b.getRecipeCrafter() == null ? new int[0] : b.getRecipeCrafter().outputSlots,
            MachineBaseBlockEntity::getStack,
            b -> b.getProgressScaled(100)
        ));
    }

}
