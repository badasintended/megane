package lol.bai.megane.runtime.config.widget;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public enum Side {
    SERVER("(S)", "server", Formatting.LIGHT_PURPLE),
    AND("(&)", "and", Formatting.YELLOW),
    PLUS("(+)", "plus", Formatting.AQUA),
    CLIENT("(C)", "client", Formatting.GREEN);

    public final Text text;
    public final Text desc;

    Side(String str, String desc, Formatting... formattings) {
        this.text = Text.literal(str).formatted(formattings);
        this.desc = Text.translatable("config.megane.side." + desc);
    }
}
