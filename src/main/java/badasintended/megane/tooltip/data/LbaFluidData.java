package badasintended.megane.tooltip.data;

import alexiil.mc.lib.attributes.fluid.FixedFluidInv;
import alexiil.mc.lib.attributes.fluid.FluidAttributes;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.World;

import static badasintended.megane.MeganeUtils.key;

public class LbaFluidData extends FluidData {

    public static final LbaFluidData INSTANCE = new LbaFluidData();

    @Override
    void appendInternal(CompoundTag data, World world, BlockEntity blockEntity) {
        FixedFluidInv attribute = FluidAttributes.FIXED_INV.getFirstOrNull(world, blockEntity.getPos());
        if (attribute == null) return;
        data.putBoolean(key("hasFluid"), true);
        int slotCount = attribute.getTankCount();
        data.putInt(key("fluidSlotCount"), slotCount);
        for (int i = 0; i < slotCount; i++) {
            data.putString(key("fluidName" + i), attribute.getInvFluid(i).getName().getString());
            data.putDouble(key("storedFluid" + i), attribute.getInvFluid(i).amount().asInt(1000));
            data.putDouble(key("maxFluid" + i), attribute.getMaxAmount_F(i).asInt(1000));
        }
    }

}
