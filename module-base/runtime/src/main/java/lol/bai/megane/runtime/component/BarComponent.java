package lol.bai.megane.runtime.component;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import lol.bai.megane.runtime.util.MeganeUtils;
import mcp.mobius.waila.api.ITooltipComponent;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import static net.minecraft.client.gui.DrawableHelper.fill;

public class BarComponent implements ITooltipComponent {

    private static final DecimalFormat FORMAT = new DecimalFormat("#.##");
    private static final Identifier TEXTURE = MeganeUtils.id("textures/bar.png");

    static {
        FORMAT.setRoundingMode(RoundingMode.DOWN);
    }

    private final int color;
    private final double stored, max;

    private final String valString;

    public BarComponent(int color, double stored, double max, String unit, boolean verbose) {
        this.color = color;
        this.stored = stored;
        this.max = max;

        String storedString;
        if (stored < 0 || stored == Double.MAX_VALUE) {
            storedString = "∞";
        } else {
            storedString = verbose ? String.valueOf(stored) : MeganeUtils.suffix((long) stored);
        }

        String maxString;
        if (max <= 0 || max == Double.MAX_VALUE) {
            maxString = "∞";
        } else {
            maxString = verbose ? String.valueOf(max) : MeganeUtils.suffix((long) max);
        }

        valString = storedString + "/" + maxString + (unit.isEmpty() ? "" : " " + unit);
    }

    @Override
    public int getWidth() {
        return Math.max(MeganeUtils.textRenderer().getWidth(valString), 100);
    }

    @Override
    public int getHeight() {
        return 13;
    }

    @Override
    public void render(MatrixStack matrices, int x, int y, float delta) {
        float ratio = max == 0 ? 1F : ((float) Math.floor((Math.min((float) (stored / max), 1F)) * 100)) / 100F;

        MeganeUtils.drawTexture(matrices, TEXTURE, x, y, 100, 11, 0, 0, 1F, 0.5F, color);
        MeganeUtils.drawTexture(matrices, TEXTURE, x, y, (int) (ratio * 100), 11, 0, 0.5F, ratio, 1F, color);

        double brightness = MeganeUtils.getBrightness(color);
        int overlay = 0;

        if (brightness < 0.25)
            overlay = 0x08FFFFFF;
        else if (brightness > 0.90)
            overlay = 0x80000000;
        else if (brightness > 0.80)
            overlay = 0x70000000;
        else if (brightness > 0.70)
            overlay = 0x60000000;
        else if (brightness > 0.60)
            overlay = 0x50000000;
        else if (brightness > 0.50)
            overlay = 0x40000000;

        fill(matrices, x, y, x + 100, y + 11, overlay);

        int textWidth = MeganeUtils.textRenderer().getWidth(valString);
        float textX = x + Math.max((100 - textWidth) / 2F, 0F);
        float textY = y + 2;
        MeganeUtils.textRenderer().draw(matrices, valString, textX, textY, 0xFFAAAAAA);
    }

}
