package lol.bai.megane.module.create.mixin;

import java.util.Map;

import com.simibubi.create.content.contraptions.MountedFluidStorage;
import com.simibubi.create.content.contraptions.MountedStorage;
import com.simibubi.create.content.contraptions.MountedStorageManager;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MountedStorageManager.class)
public interface AccessMountedStorageManager {

    @Accessor("storage")
    Map<BlockPos, MountedStorage> megane_storage();

    @Accessor("fluidStorage")
    Map<BlockPos, MountedFluidStorage> megane_fluidStorage();

}
