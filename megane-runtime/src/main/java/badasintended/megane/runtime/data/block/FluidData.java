package badasintended.megane.runtime.data.block;

import alexiil.mc.lib.attributes.fluid.FixedFluidInvView;
import alexiil.mc.lib.attributes.fluid.FluidAttributes;
import alexiil.mc.lib.attributes.misc.NullVariant;
import badasintended.megane.api.provider.FluidProvider;
import badasintended.megane.runtime.data.Appender;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import static badasintended.megane.api.registry.TooltipRegistry.FLUID;
import static badasintended.megane.runtime.util.RuntimeUtils.errorData;
import static badasintended.megane.util.MeganeUtils.*;

public class FluidData extends BlockData {

    public FluidData() {
        super(() -> config().fluid);
        appenders.add(new Registered());
        if (hasMod("libblockattributes_fluids")) {
            LOGGER.info("[megane] Found LibBlockAttributes, loading compatibility");
            appenders.add(new LibBlockAttributes());
        }
    }

    public static class Registered implements Appender<BlockEntity> {

        @Override
        @SuppressWarnings({"rawtypes", "unchecked"})
        public boolean append(CompoundTag data, ServerPlayerEntity player, World world, BlockEntity blockEntity) {
            try {
                FluidProvider provider = FLUID.get(blockEntity);
                if (provider == null || !provider.hasFluid(blockEntity)) return false;

                data.putBoolean(key("hasFluid"), true);

                int slotCount = provider.getSlotCount(blockEntity);
                data.putInt(key("fluidSlotCount"), slotCount);

                for (int i = 0; i < slotCount; i++) {
                    data.putString(key("fluidName" + i), provider.getFluidName(blockEntity, i).getString());
                    data.putDouble(key("storedFluid" + i), provider.getStored(blockEntity, i));
                    data.putDouble(key("maxFluid" + i), provider.getMax(blockEntity, i));
                }
                return true;
            } catch (Exception e) {
                errorData(FLUID, blockEntity, e);
                return false;
            }
        }

    }

    public static class LibBlockAttributes implements Appender<BlockEntity> {

        @Override
        public boolean append(CompoundTag data, ServerPlayerEntity player, World world, BlockEntity blockEntity) {
            FixedFluidInvView attribute = FluidAttributes.FIXED_INV_VIEW.getFirst(world, blockEntity.getPos());
            if (attribute instanceof NullVariant) return false;
            data.putBoolean(key("hasFluid"), true);
            data.putBoolean(key("translate"), false);
            int slotCount = attribute.getTankCount();
            data.putInt(key("fluidSlotCount"), slotCount);
            for (int i = 0; i < slotCount; i++) {
                data.putString(key("fluidName" + i), attribute.getInvFluid(i).getName().getString());
                data.putDouble(key("storedFluid" + i), attribute.getInvFluid(i).amount().asInt(1000));
                data.putDouble(key("maxFluid" + i), attribute.getMaxAmount_F(i).asInt(1000));
            }
            return true;
        }

    }

}
