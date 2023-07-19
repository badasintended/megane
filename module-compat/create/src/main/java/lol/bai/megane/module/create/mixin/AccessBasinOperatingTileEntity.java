package lol.bai.megane.module.create.mixin;

import java.util.Optional;

import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.content.processing.basin.BasinOperatingBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BasinOperatingBlockEntity.class)
public interface AccessBasinOperatingTileEntity {

    @Invoker("getBasin")
    Optional<BasinBlockEntity> megane_getBasin();

}
