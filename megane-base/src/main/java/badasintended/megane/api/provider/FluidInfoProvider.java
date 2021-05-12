package badasintended.megane.api.provider;

import java.util.function.Function;

import badasintended.megane.api.function.Functions.Obj2Int;
import badasintended.megane.api.function.Functions.ObjObj2Int;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;
import net.minecraft.world.biome.Biome;

/**
 * oh no
 */
@Environment(EnvType.CLIENT)
public interface FluidInfoProvider<T> {

    static <T> FluidInfoProvider<T> of(int color, Text name) {
        return of(t -> color, t -> name);
    }

    static <T> FluidInfoProvider<T> of(Obj2Int<T> color, Function<T, Text> name) {
        return of((t, i) -> color.apply(t), name);
    }

    static <T> FluidInfoProvider<T> of(ObjObj2Int<T, Biome> color, Function<T, Text> name) {
        return new FluidInfoProvider<T>() {
            @Override
            public int getColor(T t, Biome biome) {
                return color.apply(t, biome);
            }

            @Override
            public Text getName(T t) {
                return name.apply(t);
            }
        };
    }

    int getColor(T t, Biome biome);

    Text getName(T t);

}
