package lol.bai.megane.module.create.mixin;

import java.util.Optional;

import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.content.processing.basin.BasinOperatingBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BasinBlockEntity.class)
public interface AccessBasinTileEntity {

    @Invoker("getOperator")
    Optional<BasinOperatingBlockEntity> megane_getOperator();

}
