package badasintended.megane.runtime.config.widget;

import net.minecraft.text.*;
import net.minecraft.util.Formatting;

public enum Side {
    SERVER("(S)", "server", Formatting.LIGHT_PURPLE),
    AND("(&)", "and", Formatting.YELLOW),
    PLUS("(+)", "plus", Formatting.AQUA),
    CLIENT("(C)", "client", Formatting.GREEN);

    public final Text text;
    public final Text desc;

    Side(String str, String desc, Formatting... formattings) {
        this.text = new LiteralText(str).formatted(formattings);
        this.desc = new TranslatableText("config.megane.side." + desc);
    }
}
