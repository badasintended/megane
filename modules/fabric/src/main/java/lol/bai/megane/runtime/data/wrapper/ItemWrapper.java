package lol.bai.megane.runtime.data.wrapper;


import lol.bai.megane.api.provider.ItemProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.ItemData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;

@SuppressWarnings({"deprecation", "rawtypes"})
public class ItemWrapper<T> extends DataWrapper<ItemProvider, T> {

    private final boolean isEntity;

    public ItemWrapper(ItemProvider megane, boolean isEntity) {
        super(megane);
        this.isEntity = isEntity;
    }

    @Override
    public void appendData(IDataWriter data, IServerAccessor<T> accessor, IPluginConfig config) {
        if (isEntity) {
            if (!(accessor.getTarget() instanceof Entity)) return;
        } else {
            if (!(accessor.getTarget() instanceof BlockEntity)) return;
        }

        data.add(ItemData.class, res -> {
            BlockPos pos;
            if (accessor.getTarget() instanceof Entity entity) pos = entity.blockPosition();
            else if (accessor.getTarget() instanceof BlockEntity blockEntity) pos = blockEntity.getBlockPos();
            else return;

            setContext(accessor, pos);

            if (megane.hasItems()) res.add(ItemData.of(config).getter(megane::getStack, megane.getSlotCount()));
            if (megane.blockOtherProvider()) res.block();
        });
    }

}
