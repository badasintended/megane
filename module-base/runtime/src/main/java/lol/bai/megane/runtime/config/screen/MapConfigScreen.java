package lol.bai.megane.runtime.config.screen;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import lol.bai.megane.runtime.util.MeganeUtils;
import lol.bai.megane.runtime.util.TriConsumer;
import mcp.mobius.waila.gui.screen.ConfigScreen;
import mcp.mobius.waila.gui.widget.ButtonEntry;
import mcp.mobius.waila.gui.widget.ConfigListWidget;
import mcp.mobius.waila.util.DisplayUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

public class MapConfigScreen<K, V> extends ConfigScreen {

    private final Map<K, V> map;
    private final Function<K, String> keyStr;
    private final Function<V, String> valStr;
    private final TriConsumer<String, String, String> mapApplier;
    private final Predicate<String> keyPredicate;
    private final Predicate<String> valPredicate;

    public MapConfigScreen(Screen parent, Text title, Map<K, V> map, Function<K, String> keyStr, Function<V, String> valStr, Predicate<String> keyPredicate, Predicate<String> valPredicate, TriConsumer<String, String, String> mapApplier) {
        super(parent, title, MeganeUtils.CONFIG::save, MeganeUtils.CONFIG::invalidate);
        this.map = map;
        this.keyStr = keyStr;
        this.valStr = valStr;
        this.mapApplier = mapApplier;
        this.keyPredicate = keyPredicate;
        this.valPredicate = valPredicate;
    }

    @Override
    public ConfigListWidget getOptions() {
        ConfigListWidget options = new ConfigListWidget(this, client, width, height, 32, height - 32, 26, MeganeUtils.CONFIG::save);
        this.map.forEach((key, val) -> options.add(new PairEntry(this, options, keyStr.apply(key), valStr.apply(val), mapApplier, keyPredicate, valPredicate)));
        options.add(new ButtonEntry("config.megane.add", 100, 20, w ->
            options.children().add(options.children().size() - 1, new PairEntry(this, options, "", "", mapApplier, keyPredicate, valPredicate))
        ));
        return options;
    }

    static class PairEntry extends ConfigListWidget.Entry {

        private final TextFieldWidget keyTextField;
        private final TextFieldWidget valTextField;
        private final ButtonWidget removeButton;
        private final int textWidth;
        private String key;
        private String val;

        PairEntry(ConfigScreen screen, ConfigListWidget options, String key, String val, TriConsumer<String, String, String> consumer, Predicate<String> keyPredicate, Predicate<String> valPredicate) {
            this.textWidth = options.getRowWidth() / 2 - 12;

            this.key = key;
            this.val = val;
            this.keyTextField = new TextFieldWidget(client.textRenderer, 0, 0, textWidth, 18, ScreenTexts.EMPTY);
            this.keyTextField.setTextPredicate(keyPredicate);
            this.keyTextField.setMaxLength(256);
            this.keyTextField.setText(key);
            this.keyTextField.setChangedListener(s -> {
                String prev = this.key;
                this.key = s;
                if (!this.key.isEmpty() && !this.val.isEmpty())
                    consumer.apply(prev, this.key, this.val);
            });
            screen.addListener(this.keyTextField);

            this.valTextField = new TextFieldWidget(client.textRenderer, 0, 0, textWidth, 18, ScreenTexts.EMPTY);
            this.valTextField.setTextPredicate(valPredicate);
            this.valTextField.setMaxLength(256);
            this.valTextField.setText(val);
            this.valTextField.setChangedListener(s -> {
                String prev = this.key;
                this.val = s;
                if (!this.key.isEmpty() && !this.val.isEmpty())
                    consumer.apply(prev, this.key, this.val);
            });
            screen.addListener(this.valTextField);

            this.removeButton = DisplayUtil.createButton(0, 0, 20, 20, Text.of("X"), w -> {
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
            super.render(matrices, index, rowTop, rowLeft, width, height, mouseX, mouseY, hovered, deltaTime);

            this.keyTextField.setX(rowLeft);
            this.keyTextField.setY(rowTop + (height - keyTextField.getHeight()) / 2);
            this.keyTextField.render(matrices, mouseX, mouseY, deltaTime);

            this.valTextField.setX(rowLeft + textWidth + 4);
            this.valTextField.setY(keyTextField.getY());
            this.valTextField.render(matrices, mouseX, mouseY, deltaTime);

            this.removeButton.setX(rowLeft + (textWidth + 2) * 2);
            this.removeButton.setY(rowTop + (height - removeButton.getHeight()) / 2);
            this.removeButton.render(matrices, mouseX, mouseY, deltaTime);
        }

    }

}
