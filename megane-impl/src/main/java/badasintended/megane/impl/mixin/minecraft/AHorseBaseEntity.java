package badasintended.megane.impl.mixin.minecraft;

import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.inventory.SimpleInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(HorseBaseEntity.class)
public interface AHorseBaseEntity {

    @Accessor
    SimpleInventory getItems();

    @Invoker("getInventorySize")
    int getInvSize();

}
