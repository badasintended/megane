package badasintended.megane.tooltip.data;

import alexiil.mc.lib.attributes.fluid.FixedFluidInvView;
import alexiil.mc.lib.attributes.fluid.FluidAttributes;
import alexiil.mc.lib.attributes.misc.NullVariant;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.World;

import static badasintended.megane.util.MeganeUtils.key;

public class LbaFluidData extends FluidData {

    public static final LbaFluidData INSTANCE = new LbaFluidData();

    @Override
    void appendInternal(CompoundTag data, World world, BlockEntity blockEntity) {
        FixedFluidInvView attribute = FluidAttributes.FIXED_INV_VIEW.getFirst(world, blockEntity.getPos());
        if (attribute instanceof NullVariant) return;
        data.putBoolean(key("hasFluid"), true);
        data.putBoolean(key("translate"), false);
        int slotCount = attribute.getTankCount();
        data.putInt(key("fluidSlotCount"), slotCount);
        for (int i = 0; i < slotCount; i++) {
            data.putString(key("fluidName" + i), attribute.getInvFluid(i).getName().getString());
            data.putDouble(key("storedFluid" + i), attribute.getInvFluid(i).amount().asInt(1000));
            data.putDouble(key("maxFluid" + i), attribute.getMaxAmount_F(i).asInt(1000));
        }
    }

}
