package badasintended.megane.runtime.config.screen;

import java.util.function.Consumer;
import java.util.function.Predicate;

import badasintended.megane.runtime.config.widget.Side;
import badasintended.megane.runtime.config.widget.SidedEntry;
import badasintended.megane.util.MeganeUtils;
import mcp.mobius.waila.gui.GuiOptions;
import mcp.mobius.waila.gui.config.OptionsEntryButton;
import mcp.mobius.waila.gui.config.OptionsListWidget;
import mcp.mobius.waila.gui.config.value.OptionsEntryValueBoolean;
import mcp.mobius.waila.gui.config.value.OptionsEntryValueInput;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import static badasintended.megane.runtime.config.widget.Side.AND;
import static badasintended.megane.runtime.config.widget.Side.CLIENT;
import static badasintended.megane.runtime.config.widget.Side.PLUS;
import static badasintended.megane.runtime.config.widget.Side.SERVER;
import static badasintended.megane.util.MeganeUtils.CONFIG;
import static badasintended.megane.util.MeganeUtils.config;

public class MeganeConfigScreen extends GuiOptions {

    public MeganeConfigScreen(Screen parent) {
        super(parent, tl("gui.waila.configuration", MeganeUtils.MODID), MeganeUtils.CONFIG::save, MeganeUtils.CONFIG::invalidate);
    }

    public MeganeConfigScreen(Screen parent, Text text) {
        super(parent, text);
    }

    private static TranslatableText tl(String key, Object... args) {
        return new TranslatableText(key, args);
    }

    private static String tlKey(String type) {
        return "config.megane." + type;
    }

    private static OptionsEntryButton button(String type, ButtonWidget.PressAction pressAction) {
        return new OptionsEntryButton(tlKey(type), new ButtonWidget(0, 0, 100, 20, LiteralText.EMPTY, pressAction));
    }

    private static OptionsEntryValueBoolean bool(String type, boolean value, Consumer<Boolean> consumer) {
        return new OptionsEntryValueBoolean(tlKey(type), value, consumer);
    }

    private static <T> OptionsEntryValueInput<T> input(String type, T t, Consumer<T> consumer, Predicate<String> validator) {
        return new OptionsEntryValueInput<>(tlKey(type), t, consumer, validator);
    }

    private static SidedEntry sided(Side side, OptionsListWidget.Entry entry) {
        return new SidedEntry(side, entry);
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public OptionsListWidget getOptions() {
        OptionsListWidget options = new OptionsListWidget(this, client, width + 45, height, 32, height - 32, 30, CONFIG::save);
        options.add(button("inventory", w -> client.openScreen(new MeganeConfigScreen(this, tl(tlKey("inventory"))) {
            @Override
            public OptionsListWidget getOptions() {
                OptionsListWidget options = new OptionsListWidget(this, client, width + 45, height, 32, height - 32, 30);
                options.add(sided(AND, bool("enabled", config().inventory.isEnabled(), config().inventory::setEnabled)));
                options.add(sided(CLIENT, input("inventory.maxWidth", config().inventory.getMaxWidth(), config().inventory::setMaxWidth, s -> s.matches("^[0-9]*$"))));
                options.add(sided(CLIENT, input("inventory.maxHeight", config().inventory.getMaxHeight(), config().inventory::setMaxHeight, s -> s.matches("^[0-9]*$"))));
                options.add(sided(PLUS, button("blacklist", w -> client.openScreen(new BlacklistConfigScreen(this, tl(tlKey("inventory.blacklist")), config().inventory.getBlacklist())))));
                return options;
            }
        })));
        options.add(button("entityInventory", w -> client.openScreen(new MeganeConfigScreen(this, tl(tlKey("entityInventory"))) {
            @Override
            public OptionsListWidget getOptions() {
                OptionsListWidget options = new OptionsListWidget(this, client, width + 45, height, 32, height - 32, 30);
                options.add(sided(AND, bool("enabled", config().entityInventory.isEnabled(), config().entityInventory::setEnabled)));
                options.add(sided(CLIENT, input("inventory.maxWidth", config().entityInventory.getMaxWidth(), config().entityInventory::setMaxWidth, s -> s.matches("^[0-9]*$"))));
                options.add(sided(CLIENT, input("inventory.maxHeight", config().entityInventory.getMaxHeight(), config().entityInventory::setMaxHeight, s -> s.matches("^[0-9]*$"))));
                options.add(sided(PLUS, button("blacklist", w -> client.openScreen(new BlacklistConfigScreen(this, tl(tlKey("inventory.blacklist")), config().entityInventory.getBlacklist())))));
                return options;
            }
        })));
        options.add(button("energy", w -> client.openScreen(new MeganeConfigScreen(this, tl(tlKey("energy"))) {
            @Override
            public OptionsListWidget getOptions() {
                OptionsListWidget options = new OptionsListWidget(this, client, width + 45, height, 32, height - 32, 30);
                options.add(sided(AND, bool("enabled", config().energy.isEnabled(), config().energy::setEnabled)));
                options.add(sided(CLIENT, bool("expand", config().energy.isExpandWhenSneak(), config().energy::setExpandWhenSneak)));
                options.add(sided(CLIENT, button("energy.unit", w -> client.openScreen(new MapConfigScreen<>(
                    this, tl(tlKey("energy.unit")), config().energy.getUnits(), s -> s, s -> s, s -> s.matches("^[a-z0-9_.-]*$"), s -> true, (prev, key, val) -> {
                    config().energy.getUnits().remove(prev);
                    if (key != null && val != null) {
                        config().energy.getUnits().put(key, val);
                    }
                })))));
                options.add(sided(CLIENT, button("energy.color", w -> client.openScreen(new MapConfigScreen<>(
                    this, tl(tlKey("energy.color")), config().energy.getColors(), s -> s, Integer::toHexString, s -> s.matches("^[a-z0-9_.-]*$"), s -> s.matches("^[a-fA-F0-9]*$"), (prev, key, val) -> {
                    config().energy.getColors().remove(prev);
                    if (key != null && val != null) {
                        config().energy.getColors().put(key, Integer.parseUnsignedInt(val, 16));
                    }
                })))));
                options.add(sided(PLUS, button("blacklist", w -> client.openScreen(new BlacklistConfigScreen(this, tl(tlKey("energy.blacklist")), config().energy.getBlacklist())))));
                return options;
            }
        })));
        options.add(button("fluid", w -> client.openScreen(new MeganeConfigScreen(this, tl(tlKey("fluid"))) {
            @Override
            public OptionsListWidget getOptions() {
                OptionsListWidget options = new OptionsListWidget(this, client, width + 45, height, 32, height - 32, 30);
                options.add(sided(AND, bool("enabled", config().fluid.isEnabled(), config().fluid::setEnabled)));
                options.add(sided(CLIENT, bool("expand", config().fluid.isExpandWhenSneak(), config().fluid::setExpandWhenSneak)));
                options.add(sided(CLIENT, input("fluid.color", Integer.toHexString(config().fluid.getBarColor()), s -> config().fluid.setBarColor(Integer.parseUnsignedInt(s, 16)), s -> s.matches("^[0-9a-fA-F]*$"))));
                options.add(sided(PLUS, button("blacklist", w -> client.openScreen(new BlacklistConfigScreen(this, tl(tlKey("fluid.blacklist")), config().fluid.getBlacklist())))));
                return options;
            }
        })));
        options.add(button("progress", w -> client.openScreen(new MeganeConfigScreen(this, tl(tlKey("progress"))) {
            @Override
            public OptionsListWidget getOptions() {
                OptionsListWidget options = new OptionsListWidget(this, client, width + 45, height, 32, height - 32, 30);
                options.add(sided(AND, bool("enabled", config().progress.isEnabled(), config().progress::setEnabled)));
                options.add(sided(CLIENT, bool("progress.showWhenZero", config().progress.isShowWhenZero(), config().progress::setShowWhenZero)));
                options.add(sided(PLUS, button("blacklist", w -> client.openScreen(new BlacklistConfigScreen(this, tl(tlKey("progress.blacklist")), config().progress.getBlacklist())))));
                return options;
            }
        })));
        options.add(button("owner", w -> client.openScreen(new MeganeConfigScreen(this, tl(tlKey("owner"))) {
            @Override
            public OptionsListWidget getOptions() {
                OptionsListWidget options = new OptionsListWidget(this, client, width + 45, height, 32, height - 32, 30);
                options.add(sided(AND, bool("enabled", config().petOwner.isEnabled(), config().petOwner::setEnabled)));
                options.add(sided(SERVER, bool("owner.offline", config().petOwner.isOffline(), config().petOwner::setOffline)));
                options.add(sided(PLUS, button("blacklist", w -> client.openScreen(new BlacklistConfigScreen(this, tl(tlKey("owner.blacklist")), config().petOwner.getBlacklist())))));
                return options;
            }
        })));
        options.add(button("effect", w -> client.openScreen(new MeganeConfigScreen(this, tl(tlKey("effect"))) {
            @Override
            public OptionsListWidget getOptions() {
                OptionsListWidget options = new OptionsListWidget(this, client, width + 45, height, 32, height - 32, 30);
                options.add(sided(AND, bool("enabled", config().effect.isEnabled(), config().effect::setEnabled)));
                options.add(sided(AND, bool("effect.level", config().effect.getLevel(), config().effect::setLevel)));
                options.add(sided(AND, bool("effect.hidden", config().effect.getHidden(), config().effect::setHidden)));
                options.add(sided(CLIENT, bool("effect.roman", config().effect.isRoman(), config().effect::setRoman)));
                options.add(sided(PLUS, button("blacklist", w -> client.openScreen(new BlacklistConfigScreen(this, tl(tlKey("effect.blacklist")), config().petOwner.getBlacklist())))));
                return options;
            }
        })));

        options.add(sided(CLIENT, bool("spawnEgg", config().getSpawnEgg(), config()::setSpawnEgg)));
        options.add(sided(CLIENT, bool("playerHead", config().getPlayerHead(), config()::setPlayerHead)));

        return options;
    }

}
