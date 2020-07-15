package badasintended.megane;

import net.minecraft.text.*;
import net.minecraft.util.Formatting;

public class TextUtil {

    public static MutableText translate(String key, Object... args) {
        return new TranslatableText(Megane.ID + "." + key, args);
    }

    public static MutableText format(String text, Formatting... format) {
        return format(new LiteralText(text), format);
    }

    public static MutableText format(MutableText text, Formatting... format) {
        return text.setStyle(Style.EMPTY.withFormatting(format));
    }

}
