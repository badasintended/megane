package badasintended.megane.runtime.component.block;

import badasintended.megane.api.provider.FluidInfoProvider;
import badasintended.megane.runtime.Megane;
import badasintended.megane.runtime.registry.Registrar;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.List;
import java.util.Map;
import mcp.mobius.waila.api.IBlockAccessor;
import mcp.mobius.waila.api.IDrawableText;
import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import static badasintended.megane.runtime.util.Keys.B_COLOR;
import static badasintended.megane.runtime.util.Keys.B_LONG;
import static badasintended.megane.runtime.util.Keys.B_MAX;
import static badasintended.megane.runtime.util.Keys.B_PREFIX;
import static badasintended.megane.runtime.util.Keys.B_STORED;
import static badasintended.megane.runtime.util.Keys.B_TL;
import static badasintended.megane.runtime.util.Keys.B_UNIT;
import static badasintended.megane.runtime.util.Keys.F_HAS;
import static badasintended.megane.runtime.util.Keys.F_ID;
import static badasintended.megane.runtime.util.Keys.F_MAX;
import static badasintended.megane.runtime.util.Keys.F_SIZE;
import static badasintended.megane.runtime.util.Keys.F_STORED;
import static badasintended.megane.runtime.util.RuntimeUtils.fluidName;
import static badasintended.megane.util.MeganeUtils.CONFIG;
import static badasintended.megane.util.MeganeUtils.config;
import static badasintended.megane.util.MeganeUtils.id;

public class FluidComponent extends BlockComponent {

    private static final Identifier DEFAULT = id("default");

    static final NbtCompound DEFAULT_TAG = new NbtCompound();
    static final Int2ObjectOpenHashMap<NbtCompound> TAGS = new Int2ObjectOpenHashMap<>();

    static {
        DEFAULT_TAG.putBoolean(B_TL, false);
        DEFAULT_TAG.putString(B_UNIT, "mB");
    }

    public FluidComponent() {
        super(() -> config().fluid);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    protected void addFluid(List<Text> tooltip, IBlockAccessor accessor, NbtCompound nbt, Fluid fluid, double stored, double max) {
        BlockPos pos = accessor.getPosition();
        World world = accessor.getWorld();

        Map<Identifier, Integer> colors = config().fluid.getColors();
        boolean expand = accessor.getPlayer().isSneaking() && config().fluid.isExpandWhenSneak();

        Identifier id = Registry.FLUID.getId(fluid);
        List<FluidInfoProvider> providers = Registrar.FLUID_INFO.get(fluid);
        FluidInfoProvider provider = providers.isEmpty() ? null : providers.get(0);

        int color;
        if (colors.containsKey(id)) {
            color = colors.get(id);
        } else {
            color = provider == null
                    ? colors.computeIfAbsent(DEFAULT, s -> 0x0D0D59)
                    : provider.getColor(fluid, world.getBiome(pos)) & 0xFFFFFF;
            colors.put(id, color);
            CONFIG.save();
        }

        String name = provider == null ? fluidName(fluid) : provider.getName(fluid).getString();

        nbt.putInt(B_COLOR, color);
        nbt.putDouble(B_STORED, stored);
        nbt.putDouble(B_MAX, max);
        nbt.putBoolean(B_LONG, expand);
        nbt.putString(B_PREFIX, name);
        tooltip.add(IDrawableText.of(Megane.BAR, nbt));
    }

    @Override
    protected void append(List<Text> tooltip, IBlockAccessor accessor) {
        NbtCompound data = accessor.getServerData();
        if (data.getBoolean(F_HAS)) {
            for (int i = 0; i < data.getInt(F_SIZE); i++) {
                double stored = data.getDouble(F_STORED + i);
                if (stored == 0)
                    continue;
                double max = data.getDouble(F_MAX + i);
                Fluid fluid = Registry.FLUID.get(data.getInt(F_ID + i));
                addFluid(tooltip, accessor, TAGS.computeIfAbsent(i, k -> DEFAULT_TAG.copy()), fluid, stored, max);
            }
        }
    }

}
