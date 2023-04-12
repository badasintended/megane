package lol.bai.megane.runtime.provider.block;

import java.util.List;

import lol.bai.megane.api.provider.FluidInfoProvider;
import lol.bai.megane.api.util.BarFormat;
import lol.bai.megane.runtime.component.StorageAmountComponent;
import lol.bai.megane.runtime.registry.Registrar;
import mcp.mobius.waila.api.IBlockAccessor;
import mcp.mobius.waila.api.ITooltip;
import mcp.mobius.waila.api.component.PairComponent;
import mcp.mobius.waila.api.component.WrappedComponent;
import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static lol.bai.megane.runtime.util.Keys.F_HAS;
import static lol.bai.megane.runtime.util.Keys.F_ID;
import static lol.bai.megane.runtime.util.Keys.F_MAX;
import static lol.bai.megane.runtime.util.Keys.F_NBT;
import static lol.bai.megane.runtime.util.Keys.F_SIZE;
import static lol.bai.megane.runtime.util.Keys.F_STORED;
import static lol.bai.megane.runtime.util.MeganeUtils.config;
import static lol.bai.megane.runtime.util.MeganeUtils.fluidName;
import static lol.bai.megane.runtime.util.MeganeUtils.id;

public class FluidComponentProvider extends BlockComponentProvider {

    private static final Identifier DEFAULT = id("default");

    public FluidComponentProvider() {
        super(() -> config().fluid);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    protected void addFluid(ITooltip tooltip, IBlockAccessor accessor, Fluid fluid, @Nullable NbtCompound nbt, double stored, double max) {
        BlockPos pos = accessor.getPosition();
        World world = accessor.getWorld();

        boolean expand = accessor.getPlayer().isSneaking() && config().fluid.isExpandWhenSneak();

        List<FluidInfoProvider> providers = Registrar.FLUID_INFO.get(fluid);
        FluidInfoProvider provider = null;

        for (FluidInfoProvider p : providers) {
            p.setContext(world, pos, accessor.getHitResult(), accessor.getPlayer(), fluid);
            p.setFluidInfoContext(nbt);
            if (p.hasFluidInfo()) {
                provider = p;
                break;
            }
        }

        int color = provider != null ? provider.getColor() : 0x0D0D59;

        tooltip.addLine(new PairComponent(
            new WrappedComponent(provider == null ? fluidName(fluid) : provider.getName()),
            new StorageAmountComponent(BarFormat.FRACTION, color, stored, max, "mB", expand)));
    }

    @Override
    protected void append(ITooltip tooltip, IBlockAccessor accessor) {
        NbtCompound data = accessor.getServerData();
        if (data.getBoolean(F_HAS)) {
            for (int i = 0; i < data.getInt(F_SIZE); i++) {
                double stored = data.getDouble(F_STORED + i);
                if (stored == 0)
                    continue;
                double max = data.getDouble(F_MAX + i);
                Fluid fluid = Registries.FLUID.get(data.getInt(F_ID + i));
                NbtCompound nbt = data.contains(F_NBT) ? data.getCompound(F_NBT) : null;
                addFluid(tooltip, accessor, fluid, nbt, stored, max);
            }
        }
    }

}
