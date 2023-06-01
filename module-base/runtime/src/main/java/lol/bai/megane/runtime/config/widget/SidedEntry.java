package lol.bai.megane.runtime.config.widget;

import com.google.common.collect.ImmutableList;
import mcp.mobius.waila.gui.widget.ButtonEntry;
import mcp.mobius.waila.gui.widget.ConfigListWidget;
import mcp.mobius.waila.gui.widget.value.ConfigValue;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public class SidedEntry extends ConfigValue<Object> {

    private final ConfigListWidget.Entry entry;
    private final Side side;

    private Element listener = null;
    private Text title = ScreenTexts.EMPTY;
    private String description = "megane.empty";
    private Runnable save = () -> {};

    public SidedEntry(Side side, ConfigValue<?> value) {
        super("", value.getValue(), null, null);
        this.entry = value;
        this.side = side;
        this.listener = value.getListener();
        this.title = value.getTitle();
        this.description = value.getDescription();
        this.save = value::save;
    }

    public SidedEntry(Side side, ButtonEntry entry) {
        super("", "", null, null);
        this.entry = entry;
        this.side = side;
    }

    @Override
    protected void drawValue(MatrixStack matrices, int entryWidth, int entryHeight, int x, int y, int mouseX, int mouseY, boolean hovered, float partialTicks) {
        int sideWidth = client.textRenderer.getWidth(side.text);
        float sideY = y + (entryHeight - client.textRenderer.fontHeight) / 2f;

        this.entry.render(matrices, 0, y, x + sideWidth + 3, entryWidth - (sideWidth + 3), entryHeight, mouseX, mouseY, hovered, partialTicks);

        client.textRenderer.drawWithShadow(matrices, side.text, x, sideY, 0xFFFFFFFF);

        if (x - 3 < mouseX && mouseX < x + sideWidth + 3 && sideY - 3 < mouseY && mouseY < sideY + 3 + client.textRenderer.fontHeight) {
            client.currentScreen.renderOrderedTooltip(matrices, client.textRenderer.wrapLines(side.desc, 200), mouseX, mouseY);
        }
    }

    @Override
    public boolean isValueValid() {
        return !(entry instanceof ConfigValue<?> value) || value.isValueValid();
    }

    @Override
    protected void gatherChildren(ImmutableList.Builder<Element> children) {
        entry.children().forEach(children::add);
    }

    @Override
    public boolean match(String filter) {
        entry.category = category;
        if (entry instanceof ButtonEntry button)
            return button.match(filter);
        if (entry instanceof ConfigValue<?> value)
            return value.match(filter);
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        entry.tick();
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
    public @Nullable ButtonWidget getResetButton() {
        return entry instanceof ConfigValue<?> value ? value.getResetButton() : null;
    }

    @Override
    public Text getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

}
