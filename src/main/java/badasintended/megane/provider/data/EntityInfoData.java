package badasintended.megane.provider.data;

import mcp.mobius.waila.api.IServerDataProvider;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import static badasintended.megane.MeganeUtils.key;

public class EntityInfoData implements IServerDataProvider<LivingEntity> {

    public static final EntityInfoData INSTANCE = new EntityInfoData();

    private EntityInfoData() {
    }

    @Override
    public void appendServerData(CompoundTag data, ServerPlayerEntity player, World world, LivingEntity livingEntity) {
        data.putInt(key("armor"), livingEntity.getArmor());
        data.putFloat(key("health"), livingEntity.getHealth());
    }

}
