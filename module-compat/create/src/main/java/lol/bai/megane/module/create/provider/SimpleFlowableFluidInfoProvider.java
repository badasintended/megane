package lol.bai.megane.module.create.provider;

import java.util.HashMap;
import java.util.Map;

import com.simibubi.create.AllFluids;
import com.tterrag.registrate.fabric.SimpleFlowableFluid;
import com.tterrag.registrate.util.entry.FluidEntry;
import lol.bai.megane.api.provider.FluidInfoProvider;
import net.minecraft.fluid.Fluid;
import net.minecraft.text.Text;

public class SimpleFlowableFluidInfoProvider extends FluidInfoProvider<SimpleFlowableFluid> {

    private final static Entry NONE = new Entry(0x0, Text.of("ERROR PLZ REPORT"));

    private final static Map<FluidEntry<?>, Entry> MAP = Map.of(
        AllFluids.TEA, new Entry(0xC56A4D, Text.translatable("fluid.create.tea")),
        AllFluids.HONEY, new Entry(0xF0A90E, Text.translatable("block.create.honey")),
        AllFluids.CHOCOLATE, new Entry(0x8E4137, Text.translatable("block.create.chocolate"))
    );

    private final static Map<Fluid, Entry> CACHE = new HashMap<>();

    private Entry entry;

    @Override
    protected void init() {
        entry = CACHE.computeIfAbsent(getObject(), fluid -> MAP
            .entrySet()
            .stream()
            .filter(pair -> pair.getKey().is(fluid))
            .findFirst()
            .map(Map.Entry::getValue)
            .orElse(NONE));
    }

    @Override
    public boolean hasFluidInfo() {
        return entry != NONE;
    }

    @Override
    public int getColor() {
        return entry.color();
    }

    @Override
    public Text getName() {
        return entry.name();
    }

    private record Entry(int color, Text name) {

    }

}
