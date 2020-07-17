package badasintended.megane;

import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public final class Utils {

    private static final NavigableMap<Long, String> SUFFIXES = new TreeMap<>();

    static {
        SUFFIXES.put(1000L, "K");
        SUFFIXES.put(1000000L, "M");
        SUFFIXES.put(1000000000L, "G");
        SUFFIXES.put(1000000000000L, "T");
        SUFFIXES.put(1000000000000000L, "P");
        SUFFIXES.put(1000000000000000000L, "E");
    }

    public static MutableText tlText(String key, Object... args) {
        return new TranslatableText(PluginMegane.ID + "." + key, args);
    }

    public static String tlString(String key, Object... args) {
        return I18n.translate(PluginMegane.ID + "." + key, args);
    }

    public static MutableText format(String text, Formatting... format) {
        return format(new LiteralText(text), format);
    }

    public static MutableText format(MutableText text, Formatting... format) {
        return text.setStyle(Style.EMPTY.withFormatting(format));
    }

    public static String key(String key) {
        return PluginMegane.ID + ".data." + key;
    }

    public static String suffix(long value) {
        if (value == Long.MIN_VALUE) return suffix(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + suffix(-value);
        if (value < 1000) return Long.toString(value);

        Map.Entry<Long, String> e = SUFFIXES.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();
        long truncated = value / (divideBy / 10);
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }

}
