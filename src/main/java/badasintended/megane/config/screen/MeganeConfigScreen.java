package badasintended.megane.config.screen;

import mcp.mobius.waila.gui.GuiOptions;
import mcp.mobius.waila.gui.config.OptionsEntryButton;
import mcp.mobius.waila.gui.config.OptionsListWidget;
import mcp.mobius.waila.gui.config.value.OptionsEntryValueBoolean;
import mcp.mobius.waila.gui.config.value.OptionsEntryValueInput;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.TranslatableText;

import java.util.function.Consumer;
import java.util.function.Predicate;

import static badasintended.megane.util.MeganeUtils.*;

public class MeganeConfigScreen extends GuiOptions {

    public MeganeConfigScreen(Screen parent) {
        super(parent, tl("gui.waila.configuration", MODID), CONFIG::save, CONFIG::invalidate);
    }

    private static TranslatableText tl(String key, Object... args) {
        return new TranslatableText(key, args);
    }

    private static String tlKey(String type) {
        return "config.waila.megane." + type;
    }

    private static OptionsEntryButton button(String type, ButtonWidget.PressAction pressAction) {
        return new OptionsEntryButton(tlKey(type), new ButtonWidget(0, 0, 100, 20, null, pressAction));
    }

    private static OptionsEntryValueBoolean toggleBool(String type, boolean value, Consumer<Boolean> consumer) {
        return new OptionsEntryValueBoolean(tlKey(type), value, consumer);
    }

    private static <T> OptionsEntryValueInput<T> input(String type, T t, Consumer<T> consumer, Predicate<String> validator) {
        return new OptionsEntryValueInput<>(tlKey(type), t, consumer, validator);
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public OptionsListWidget getOptions() {
        OptionsListWidget options = new OptionsListWidget(this, client, width + 45, height, 32, height - 32, 30, CONFIG::save);
        options.add(button("inventory", w -> client.openScreen(new GuiOptions(this, tl(tlKey("inventory"))) {
            @Override
            public OptionsListWidget getOptions() {
                OptionsListWidget options = new OptionsListWidget(this, client, width + 45, height, 32, height - 32, 30);
                options.add(toggleBool("enabled", config().inventory.isEnabled(), config().inventory::setEnabled));
                options.add(input("inventory.maxWidth", config().inventory.getMaxWidth(), config().inventory::setMaxWidth, s -> s.matches("^[0-9]*$")));
                options.add(input("inventory.maxHeight", config().inventory.getMaxHeight(), config().inventory::setMaxHeight, s -> s.matches("^[0-9]*$")));
                options.add(button("blacklist", w -> client.openScreen(new BlacklistConfigScreen(this, tl(tlKey("inventory.blacklist")), config().inventory.getBlacklist()))));
                return options;
            }
        })));
        options.add(button("energy", w -> client.openScreen(new GuiOptions(this, tl(tlKey("energy"))) {
            @Override
            public OptionsListWidget getOptions() {
                OptionsListWidget options = new OptionsListWidget(this, client, width + 45, height, 32, height - 32, 30);
                options.add(toggleBool("enabled", config().energy.isEnabled(), config().energy::setEnabled));
                options.add(toggleBool("expand", config().energy.isExpandWhenSneak(), config().energy::setExpandWhenSneak));
                options.add(button("energy.unit", w -> client.openScreen(new MapConfigScreen<>(
                    this, tl(tlKey("energy.unit")), config().energy.getUnits(), s -> s, s -> s, s -> s.matches("^[a-z0-9_.-]*$"), s -> true, (prev, key, val) -> {
                    config().energy.getUnits().remove(prev);
                    if (key != null && val != null) {
                        config().energy.getUnits().put(key, val);
                    }
                }))));
                options.add(button("energy.color", w -> client.openScreen(new MapConfigScreen<>(
                    this, tl(tlKey("energy.color")), config().energy.getColors(), s -> s, Integer::toHexString, s -> s.matches("^[a-z0-9_.-]*$"), s -> s.matches("^[a-fA-F0-9]*$"), (prev, key, val) -> {
                    config().energy.getColors().remove(prev);
                    if (key != null && val != null) {
                        config().energy.getColors().put(key, Integer.parseUnsignedInt(val, 16));
                    }
                }))));
                options.add(button("blacklist", w -> client.openScreen(new BlacklistConfigScreen(this, tl(tlKey("energy.blacklist")), config().energy.getBlacklist()))));
                return options;
            }
        })));
        options.add(button("fluid", w -> client.openScreen(new GuiOptions(this, tl(tlKey("fluid"))) {
            @Override
            public OptionsListWidget getOptions() {
                OptionsListWidget options = new OptionsListWidget(this, client, width + 45, height, 32, height - 32, 30);
                options.add(toggleBool("enabled", config().fluid.isEnabled(), config().fluid::setEnabled));
                options.add(toggleBool("expand", config().fluid.isExpandWhenSneak(), config().fluid::setExpandWhenSneak));
                options.add(input("fluid.color", Integer.toHexString(config().fluid.getBarColor()), s -> config().fluid.setBarColor(Integer.parseUnsignedInt(s, 16)), s -> s.matches("^[0-9a-fA-F]*$")));
                options.add(button("blacklist", w -> client.openScreen(new BlacklistConfigScreen(this, tl(tlKey("fluid.blacklist")), config().fluid.getBlacklist()))));
                return options;
            }
        })));
        options.add(button("progress", w -> client.openScreen(new GuiOptions(this, tl(tlKey("progress"))) {
            @Override
            public OptionsListWidget getOptions() {
                OptionsListWidget options = new OptionsListWidget(this, client, width + 45, height, 32, height - 32, 30);
                options.add(toggleBool("enabled", config().progress.isEnabled(), config().progress::setEnabled));
                options.add(button("blacklist", w -> client.openScreen(new BlacklistConfigScreen(this, tl(tlKey("progress.blacklist")), config().progress.getBlacklist()))));
                return options;
            }
        })));
        return options;
    }

}
