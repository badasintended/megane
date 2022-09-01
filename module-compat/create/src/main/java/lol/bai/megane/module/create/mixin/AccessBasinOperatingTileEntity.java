package lol.bai.megane.module.create.mixin;

import java.util.Optional;

import com.simibubi.create.content.contraptions.processing.BasinOperatingTileEntity;
import com.simibubi.create.content.contraptions.processing.BasinTileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BasinOperatingTileEntity.class)
public interface AccessBasinOperatingTileEntity {

    @Invoker("getBasin")
    Optional<BasinTileEntity> megane_getBasin();

}
