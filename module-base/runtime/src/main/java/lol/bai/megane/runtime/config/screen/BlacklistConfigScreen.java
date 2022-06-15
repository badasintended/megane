package lol.bai.megane.runtime.config.screen;

import java.util.Set;

import lol.bai.megane.runtime.util.MeganeUtils;
import mcp.mobius.waila.gui.screen.ConfigScreen;
import mcp.mobius.waila.gui.widget.ButtonEntry;
import mcp.mobius.waila.gui.widget.ConfigListWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class BlacklistConfigScreen extends ConfigScreen {

    private final Set<Identifier> set;

    public BlacklistConfigScreen(Screen parent, Text title, Set<Identifier> set) {
        super(parent, title, MeganeUtils.CONFIG::save, MeganeUtils.CONFIG::invalidate);
        this.set = set;
    }

    @Override
    public ConfigListWidget getOptions() {
        ConfigListWidget options = new ConfigListWidget(this, client, width, height, 32, height - 32, 26, MeganeUtils.CONFIG::save);
        this.set.forEach(value -> options.add(new SetEntry(this, options, value, this.set)));
        options.add(new ButtonEntry("config.megane.add", new ButtonWidget(0, 0, 100, 20, LiteralText.EMPTY, w ->
            options.children().add(options.children().size() - 1, new SetEntry(this, options, null, set))
        )));
        return options;
    }

    static class SetEntry extends ConfigListWidget.Entry {

        private final TextFieldWidget textField;
        private final ButtonWidget removeButton;
        private Identifier value;

        SetEntry(ConfigScreen screen, ConfigListWidget options, Identifier value, Set<Identifier> set) {
            this.value = value;
            this.textField = new TextFieldWidget(client.textRenderer, 0, 0, options.getRowWidth() - 22, 18, new LiteralText(""));
            this.textField.setTextPredicate(s -> s.matches("^[a-z0-9/_.-]*$") || s.matches("^[a-z0-9_.-]*:[a-z0-9/._-]*$"));
            this.textField.setMaxLength(256);
            if (value != null)
                this.textField.setText(value.toString());
            this.textField.setChangedListener(s -> {
                set.remove(this.value);
                this.value = new Identifier(s);
                if (!this.value.getPath().isEmpty())
                    set.add(this.value);
            });

            this.removeButton = new ButtonWidget(0, 0, 20, 20, new LiteralText("X"), w -> {
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
            super.render(matrices, index, rowTop, rowLeft, width, height, mouseX, mouseY, hovered, deltaTime);

            this.textField.x = rowLeft;
            this.textField.y = rowTop;
            this.textField.render(matrices, mouseX, mouseY, deltaTime);

            this.removeButton.x = rowLeft + textField.getWidth() + 2;
            this.removeButton.y = rowTop + (height - removeButton.getHeight()) / 2;
            this.removeButton.render(matrices, mouseX, mouseY, deltaTime);
        }

    }

}
