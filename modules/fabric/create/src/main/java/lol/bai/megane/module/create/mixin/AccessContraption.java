package lol.bai.megane.module.create.mixin;

import com.simibubi.create.content.contraptions.Contraption;
import com.simibubi.create.content.contraptions.MountedStorageManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Contraption.class)
public interface AccessContraption {

    @Invoker("getStorageForSpawnPacket")
    MountedStorageManager megane_getStorageForSpawnPacket();

}
