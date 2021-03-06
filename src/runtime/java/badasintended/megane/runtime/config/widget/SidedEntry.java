package badasintended.megane.runtime.config.widget;

import mcp.mobius.waila.gui.widget.ConfigListWidget;
import mcp.mobius.waila.gui.widget.value.ConfigValue;
import net.minecraft.client.gui.Element;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class SidedEntry extends ConfigValue<Object> {

    private final ConfigListWidget.Entry entry;
    private final Side side;

    private Element listener = null;
    private Text title = LiteralText.EMPTY;
    private String description = "megane.empty";
    private Runnable save = () -> {
    };

    public SidedEntry(Side side, ConfigListWidget.Entry entry) {
        super("", null);
        this.entry = entry;
        this.side = side;

        if (entry instanceof ConfigValue<?> value) {
            this.listener = value.getListener();
            this.title = value.getTitle();
            this.description = value.getDescription();
            this.save = value::save;
        }
    }

    @Override
    protected void drawValue(MatrixStack matrices, int entryWidth, int entryHeight, int x, int y, int mouseX, int mouseY, boolean selected, float partialTicks) {
        this.entry.render(matrices, 0, y, x, entryWidth, entryHeight, mouseX, mouseY, selected, partialTicks);

        float sideX = x - 5 - client.textRenderer.getWidth(side.text);
        float sideY = y + (entryHeight - client.textRenderer.fontHeight) / 2f;

        client.textRenderer.drawWithShadow(matrices, side.text, sideX, sideY, 0xFFFFFFFF);

        if (sideX - 3 < mouseX && mouseX < sideX + 3 + client.textRenderer.getWidth(side.text) && sideY - 3 < mouseY && mouseY < sideY + 3 + client.textRenderer.fontHeight) {
            client.currentScreen.renderOrderedTooltip(matrices, client.textRenderer.wrapLines(side.desc, 200), mouseX, mouseY);
        }
    }

    @Override
    public void save() {
        save.run();
    }

    @Override
    public Element getListener() {
        return listener;
    }

    @Override
    public Text getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return entry.mouseClicked(mouseX, mouseY, button);
    }

}
