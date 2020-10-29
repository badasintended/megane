package badasintended.megane.impl.mixin.modern_industrialization;

import aztech.modern_industrialization.machines.impl.multiblock.HatchBlockEntity;
import aztech.modern_industrialization.machines.impl.multiblock.MultiblockMachineBlockEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(MultiblockMachineBlockEntity.class)
public interface AMultiblockMachineBlockEntity {

    @Accessor
    Map<BlockPos, HatchBlockEntity> getLinkedHatches();

}
