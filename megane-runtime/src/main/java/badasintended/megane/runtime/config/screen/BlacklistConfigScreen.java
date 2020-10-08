package badasintended.megane.runtime.config.screen;

import mcp.mobius.waila.gui.GuiOptions;
import mcp.mobius.waila.gui.config.OptionsEntryButton;
import mcp.mobius.waila.gui.config.OptionsListWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Set;

import static badasintended.megane.util.MeganeUtils.CONFIG;

public class BlacklistConfigScreen extends GuiOptions {

    private final Set<Identifier> set;

    public BlacklistConfigScreen(Screen parent, Text title, Set<Identifier> set) {
        super(parent, title, CONFIG::save, CONFIG::invalidate);
        this.set = set;
    }

    @Override
    public OptionsListWidget getOptions() {
        OptionsListWidget options = new OptionsListWidget(this, client, width + 45, height, 32, height - 32, 30, CONFIG::save);
        this.set.forEach(value -> options.add(new SetEntry(this, options, value, this.set)));
        options.add(new OptionsEntryButton("config.megane.add", new ButtonWidget(0, 0, 100, 20, null, w ->
            options.children().add(options.children().size() - 1, new SetEntry(this, options, null, set))
        )));
        return options;
    }

    static class SetEntry extends OptionsListWidget.Entry {

        private final TextFieldWidget textField;
        private final ButtonWidget removeButton;
        private Identifier value;

        SetEntry(GuiOptions screen, OptionsListWidget options, Identifier value, Set<Identifier> set) {
            this.value = value;
            this.textField = new TextFieldWidget(client.textRenderer, 0, 0, 200, 18, new LiteralText(""));
            this.textField.setTextPredicate(s -> s.matches("^[a-z0-9/_.-]*$") || s.matches("^[a-z0-9_.-]*:[a-z0-9/._-]*$"));
            this.textField.setMaxLength(256);
            if (value != null) this.textField.setText(value.toString());
            this.textField.setChangedListener(s -> {
                set.remove(this.value);
                this.value = new Identifier(s);
                if (!this.value.getPath().isEmpty()) set.add(this.value);
            });

            this.removeButton = new ButtonWidget(0, 0, 18, 18, new LiteralText("X"), w -> {
                set.remove(this.value);
                screen.children().remove(this.textField);
                screen.children().remove(w);
                options.children().remove(this);
            });

            screen.addListener(this.textField);
            screen.addListener(this.removeButton);
        }

        @Override
        public void render(MatrixStack matrices, int index, int rowTop, int rowLeft, int width, int height, int mouseX, int mouseY, boolean hovered, float deltaTime) {
            this.textField.x = rowLeft + 10;
            this.textField.y = rowTop + height / 6;
            this.textField.render(matrices, mouseX, mouseY, deltaTime);

            this.removeButton.x = rowLeft + 212;
            this.removeButton.y = rowTop + height / 6;
            this.removeButton.render(matrices, mouseX, mouseY, deltaTime);
        }

    }

}
