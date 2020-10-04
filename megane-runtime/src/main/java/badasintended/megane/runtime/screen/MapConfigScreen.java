package badasintended.megane.runtime.screen;

import badasintended.megane.util.TriConsumer;
import mcp.mobius.waila.gui.GuiOptions;
import mcp.mobius.waila.gui.config.OptionsEntryButton;
import mcp.mobius.waila.gui.config.OptionsListWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import static badasintended.megane.util.MeganeUtils.CONFIG;

public class MapConfigScreen<K, V> extends GuiOptions {

    private final Map<K, V> map;
    private final Function<K, String> keyStr;
    private final Function<V, String> valStr;
    private final TriConsumer<String, String, String> mapApplier;
    private final Predicate<String> keyPredicate;
    private final Predicate<String> valPredicate;

    public MapConfigScreen(Screen parent, Text title, Map<K, V> map, Function<K, String> keyStr, Function<V, String> valStr, Predicate<String> keyPredicate, Predicate<String> valPredicate, TriConsumer<String, String, String> mapApplier) {
        super(parent, title, CONFIG::save, CONFIG::invalidate);
        this.map = map;
        this.keyStr = keyStr;
        this.valStr = valStr;
        this.mapApplier = mapApplier;
        this.keyPredicate = keyPredicate;
        this.valPredicate = valPredicate;
    }

    @Override
    public OptionsListWidget getOptions() {
        OptionsListWidget options = new OptionsListWidget(this, client, width + 45, height, 32, height - 32, 30, CONFIG::save);
        this.map.forEach((key, val) -> options.add(new PairEntry(this, options, keyStr.apply(key), valStr.apply(val), mapApplier, keyPredicate, valPredicate)));
        options.add(new OptionsEntryButton("config.megane.add", new ButtonWidget(0, 0, 100, 20, null, w ->
            options.children().add(options.children().size() - 1, new PairEntry(this, options, "", "", mapApplier, keyPredicate, valPredicate))
        )));
        return options;
    }

    static class PairEntry extends OptionsListWidget.Entry {

        private final TextFieldWidget keyTextField;
        private final TextFieldWidget valTextField;
        private final ButtonWidget removeButton;
        private String key;
        private String val;

        PairEntry(GuiOptions screen, OptionsListWidget options, String key, String val, TriConsumer<String, String, String> consumer, Predicate<String> keyPredicate, Predicate<String> valPredicate) {
            this.key = key;
            this.val = val;
            this.keyTextField = new TextFieldWidget(client.textRenderer, 0, 0, 100, 18, new LiteralText(""));
            this.keyTextField.setTextPredicate(keyPredicate);
            this.keyTextField.setText(key);
            this.keyTextField.setMaxLength(256);
            this.keyTextField.setChangedListener(s -> {
                String prev = this.key;
                this.key = s;
                if (!this.key.isEmpty() && !this.val.isEmpty()) consumer.apply(prev, this.key, this.val);
            });
            screen.addListener(this.keyTextField);

            this.valTextField = new TextFieldWidget(client.textRenderer, 0, 0, 100, 18, new LiteralText(""));
            this.valTextField.setTextPredicate(valPredicate);
            this.valTextField.setText(val);
            this.valTextField.setMaxLength(256);
            this.valTextField.setChangedListener(s -> {
                String prev = this.key;
                this.val = s;
                if (!this.key.isEmpty() && !this.val.isEmpty()) consumer.apply(prev, this.key, this.val);
            });
            screen.addListener(this.valTextField);

            this.removeButton = new ButtonWidget(0, 0, 18, 18, new LiteralText("X"), w -> {
                consumer.apply(this.key, null, null);
                screen.children().remove(this.keyTextField);
                screen.children().remove(this.valTextField);
                screen.children().remove(w);
                options.children().remove(this);
            });
            screen.addListener(this.removeButton);
        }

        @Override
        public void render(MatrixStack matrices, int index, int rowTop, int rowLeft, int width, int height, int mouseX, int mouseY, boolean hovered, float deltaTime) {
            this.keyTextField.x = rowLeft + 10;
            this.keyTextField.y = rowTop + height / 6;
            this.keyTextField.render(matrices, mouseX, mouseY, deltaTime);

            this.valTextField.x = rowLeft + 114;
            this.valTextField.y = rowTop + height / 6;
            this.valTextField.render(matrices, mouseX, mouseY, deltaTime);

            this.removeButton.x = rowLeft + 216;
            this.removeButton.y = rowTop + height / 6;
            this.removeButton.render(matrices, mouseX, mouseY, deltaTime);
        }

    }

}
