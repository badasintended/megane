package badasintended.megane.runtime.data;

import alexiil.mc.lib.attributes.fluid.FixedFluidInvView;
import alexiil.mc.lib.attributes.fluid.FluidAttributes;
import alexiil.mc.lib.attributes.misc.NullVariant;
import badasintended.megane.api.registry.FluidTooltipRegistry;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import static badasintended.megane.api.registry.FluidTooltipRegistry.get;
import static badasintended.megane.util.MeganeUtils.*;

public class FluidData extends BaseData {

    public static final FluidData INSTANCE = new FluidData();

    private FluidData() {
        super(() -> config().fluid);
        appenders.add(new Registered());
        if (hasMod("libblockattributes_fluids")) {
            LOGGER.info("[megane] Found LibBlockAttributes, loading compatibility");
            appenders.add(new LibBlockAttributes());
        }
    }

    public static class Registered implements Appender {

        @Override
        @SuppressWarnings({"rawtypes", "unchecked"})
        public boolean append(CompoundTag data, ServerPlayerEntity player, World world, BlockEntity blockEntity) {
            FluidTooltipRegistry.Provider provider = get(blockEntity);
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
        }

    }

    public static class LibBlockAttributes implements Appender {

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
