package lol.bai.megane.runtime.component;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import lol.bai.megane.api.util.BarFormat;
import lol.bai.megane.runtime.util.MeganeUtils;
import mcp.mobius.waila.api.ITooltipComponent;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import static net.minecraft.client.gui.DrawableHelper.fill;

public class StorageAmountComponent implements ITooltipComponent {

    private static final DecimalFormat FORMAT = new DecimalFormat("#.##");
    private static final Identifier TEXTURE = MeganeUtils.id("textures/bar.png");
    private static final String INFINITE = "\u221E";

    static {
        FORMAT.setRoundingMode(RoundingMode.DOWN);
    }

    private final int color;
    private final double stored, max;

    private final boolean drawBar;
    private final String valString;

    public StorageAmountComponent(BarFormat format, int color, double stored, double max, String unit, boolean verbose) {
        this.color = color;
        this.stored = stored;
        this.max = max;

        if (isInfinite(max)) {
            drawBar = false;
            valString = createValStr(createNumberStr(stored, verbose), unit);
            return;
        }

        String number = switch (format) {
            case FRACTION -> {
                String storedString = createNumberStr(stored, verbose);
                String maxString = max == 0 ? INFINITE : createNumberStr(max, verbose);
                yield storedString + '/' + maxString;
            }
            case PERCENT -> {
                if (isInfinite(stored) || max == 0 || isInfinite(max)) {
                    yield INFINITE + '%';
                }

                yield String.valueOf(stored / max * 100) + '%';
            }
        };

        drawBar = true;
        valString = number + (unit.isEmpty() ? "" : " " + unit);
    }

    private static String createNumberStr(double number, boolean verbose) {
        return isInfinite(number) ? INFINITE
            : verbose ? String.valueOf(number) : MeganeUtils.suffix((long) number);
    }

    private static String createValStr(String number, String unit) {
        return number + (unit.isEmpty() ? "" : " " + unit);
    }

    private static boolean isInfinite(double number) {
        return number < 0 || number == Double.MAX_VALUE || number == Double.POSITIVE_INFINITY;
    }

    @Override
    public int getWidth() {
        return Math.max(MeganeUtils.textRenderer().getWidth(valString), drawBar ? 100 : -1);
    }

    @Override
    public int getHeight() {
        return drawBar ? 13 : MeganeUtils.textRenderer().fontHeight;
    }

    @Override
    public void render(MatrixStack matrices, int x, int y, float delta) {
        if (!drawBar) {
            MeganeUtils.textRenderer().draw(matrices, valString, x, y, 0xFF000000 | color);
            return;
        }

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
