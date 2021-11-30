package badasintended.megane.runtime.config.screen;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import badasintended.megane.config.MeganeConfig;
import badasintended.megane.runtime.config.widget.Side;
import badasintended.megane.runtime.config.widget.SidedEntry;
import badasintended.megane.util.MeganeUtils;
import mcp.mobius.waila.gui.screen.ConfigScreen;
import mcp.mobius.waila.gui.widget.ButtonEntry;
import mcp.mobius.waila.gui.widget.CategoryEntry;
import mcp.mobius.waila.gui.widget.ConfigListWidget;
import mcp.mobius.waila.gui.widget.value.BooleanValue;
import mcp.mobius.waila.gui.widget.value.InputValue;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

import static badasintended.megane.runtime.config.widget.Side.AND;
import static badasintended.megane.runtime.config.widget.Side.CLIENT;
import static badasintended.megane.runtime.config.widget.Side.PLUS;
import static badasintended.megane.runtime.config.widget.Side.SERVER;
import static badasintended.megane.util.MeganeUtils.CONFIG;
import static badasintended.megane.util.MeganeUtils.config;

public class MeganeConfigScreen extends ConfigScreen {

    private static final Predicate<String> ALL = s -> true;
    private static final Predicate<String> HEX = s -> s.matches("^[a-fA-F0-9]*$");
    private static final Predicate<String> INT = s -> s.matches("^[0-9]*$");
    private static final Predicate<String> NAMESPACE = s -> s.matches("^[a-z0-9_.-]*$");
    private static final Predicate<String> IDENTIFIER = s -> s.matches("^[a-z0-9_./-]*$") || s.matches("^[a-z0-9_.-]*:[a-z0-9_./-]*$");

    private static final Function<Integer, String> INT2RGB = i -> {
        StringBuilder rgb = new StringBuilder(Integer.toHexString(i));
        for (int j = 0; j < (6 - rgb.length()); j++) {
            rgb.insert(0, "0");
        }
        return rgb.toString();
    };

    private final MeganeConfig def = new MeganeConfig();

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

    private static CategoryEntry category(String type) {
        return new CategoryEntry(tlKey(type));
    }

    private static ButtonEntry button(String type, ButtonWidget.PressAction pressAction) {
        return new ButtonEntry(tlKey(type), new ButtonWidget(0, 0, 100, 20, LiteralText.EMPTY, pressAction));
    }

    private static BooleanValue bool(String type, boolean value, boolean defaultValue, Consumer<Boolean> consumer) {
        return new BooleanValue(tlKey(type), value, defaultValue, consumer);
    }

    private static <T> InputValue<T> input(String type, T t, T defaultValue, Consumer<T> consumer, Predicate<String> validator) {
        return new InputValue<>(tlKey(type), t, defaultValue, consumer, validator);
    }

    private static SidedEntry sided(Side side, ConfigListWidget.Entry entry) {
        return new SidedEntry(side, entry);
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public ConfigListWidget getOptions() {
        ConfigListWidget options = new ConfigListWidget(this, client, width, height, 32, height - 32, 26, CONFIG::save);
        options
            .with(category("inventory"))
            .with(sided(AND, bool("enabled", config().inventory.isEnabled(), def.inventory.isEnabled(), config().inventory::setEnabled)))
            .with(sided(SERVER, bool("inventory.itemCount", config().inventory.isItemCount(), def.inventory.isItemCount(), config().inventory::setItemCount)))
            .with(sided(SERVER, bool("inventory.nbt", config().inventory.isNbt(), def.inventory.isNbt(), config().inventory::setNbt)))
            .with(sided(CLIENT, input("inventory.maxWidth", config().inventory.getMaxWidth(), def.inventory.getMaxWidth(), config().inventory::setMaxWidth, INT)))
            .with(sided(CLIENT, input("inventory.maxHeight", config().inventory.getMaxHeight(), def.inventory.getMaxHeight(), config().inventory::setMaxHeight, INT)))
            .with(sided(PLUS, button("blacklist", w -> client.setScreen(new BlacklistConfigScreen(this, tl(tlKey("inventory.blacklist")), config().inventory.getBlacklist())))))

            .with(category("entityInventory"))
            .with(sided(AND, bool("enabled", config().entityInventory.isEnabled(), def.entityInventory.isEnabled(), config().entityInventory::setEnabled)))
            .with(sided(SERVER, bool("inventory.itemCount", config().entityInventory.isItemCount(), def.entityInventory.isItemCount(), config().entityInventory::setItemCount)))
            .with(sided(SERVER, bool("inventory.nbt", config().entityInventory.isNbt(), def.entityInventory.isNbt(), config().entityInventory::setNbt)))
            .with(sided(CLIENT, input("inventory.maxWidth", config().entityInventory.getMaxWidth(), def.entityInventory.getMaxWidth(), config().entityInventory::setMaxWidth, INT)))
            .with(sided(CLIENT, input("inventory.maxHeight", config().entityInventory.getMaxHeight(), def.entityInventory.getMaxHeight(), config().entityInventory::setMaxHeight, INT)))
            .with(sided(PLUS, button("blacklist", w -> client.setScreen(new BlacklistConfigScreen(this, tl(tlKey("inventory.blacklist")), config().entityInventory.getBlacklist())))))

            .with(category("energy"))
            .with(sided(AND, bool("enabled", config().energy.isEnabled(), def.energy.isEnabled(), config().energy::setEnabled)))
            .with(sided(CLIENT, bool("expand", config().energy.isExpandWhenSneak(), def.energy.isExpandWhenSneak(), config().energy::setExpandWhenSneak)))
            .with(sided(CLIENT, button("energy.unit", w -> client.setScreen(new MapConfigScreen<>(
                this, tl(tlKey("energy.unit")), config().energy.getUnits(), s -> s, s -> s, NAMESPACE, ALL, (prev, key, val) -> {
                config().energy.getUnits().remove(prev);
                if (key != null && val != null) {
                    config().energy.getUnits().put(key, val);
                }
            })))))
            .with(sided(CLIENT, button("energy.color", w -> client.setScreen(new MapConfigScreen<>(
                this, tl(tlKey("energy.color")), config().energy.getColors(), s -> s, INT2RGB, NAMESPACE, HEX, (prev, key, val) -> {
                config().energy.getColors().remove(prev);
                if (key != null && val != null) {
                    config().energy.getColors().put(key, Integer.parseUnsignedInt(val, 16) & 0xFFFFFF);
                }
            })))))
            .with(sided(PLUS, button("blacklist", w -> client.setScreen(new BlacklistConfigScreen(this, tl(tlKey("energy.blacklist")), config().energy.getBlacklist())))))

            .with(category("fluid"))
            .with(sided(AND, bool("enabled", config().fluid.isEnabled(), def.fluid.isEnabled(), config().fluid::setEnabled)))
            .with(sided(CLIENT, bool("expand", config().fluid.isExpandWhenSneak(), def.fluid.isExpandWhenSneak(), config().fluid::setExpandWhenSneak)))
            .with(sided(CLIENT, button("fluid.color", w -> client.setScreen(new MapConfigScreen<>(
                this, tl(tlKey("fluid.color")), config().fluid.getColors(), Identifier::toString, INT2RGB, IDENTIFIER, HEX, (prev, key, val) -> {
                config().fluid.getColors().remove(new Identifier(prev));
                if (key != null && val != null) {
                    config().fluid.getColors().put(new Identifier(prev), Integer.parseUnsignedInt(val, 16) & 0xFFFFFF);
                }
            })))))
            .with(sided(PLUS, button("blacklist", w -> client.setScreen(new BlacklistConfigScreen(this, tl(tlKey("fluid.blacklist")), config().fluid.getBlacklist())))))

            .with(category("progress"))
            .with(sided(AND, bool("enabled", config().progress.isEnabled(), def.progress.isEnabled(), config().progress::setEnabled)))
            .with(sided(CLIENT, bool("progress.showWhenZero", config().progress.isShowWhenZero(), def.progress.isShowWhenZero(), config().progress::setShowWhenZero)))
            .with(sided(PLUS, button("blacklist", w -> client.setScreen(new BlacklistConfigScreen(this, tl(tlKey("progress.blacklist")), config().progress.getBlacklist())))))

            .with(category("effect"))
            .with(sided(AND, bool("enabled", config().effect.isEnabled(), def.effect.isEnabled(), config().effect::setEnabled)))
            .with(sided(AND, bool("effect.level", config().effect.getLevel(), def.effect.getLevel(), config().effect::setLevel)))
            .with(sided(AND, bool("effect.hidden", config().effect.getHidden(), def.effect.getHidden(), config().effect::setHidden)))
            .with(sided(CLIENT, bool("effect.roman", config().effect.isRoman(), def.effect.isRoman(), config().effect::setRoman)))
            .with(sided(PLUS, button("blacklist", w -> client.setScreen(new BlacklistConfigScreen(this, tl(tlKey("effect.blacklist")), config().effect.getBlacklist())))))

            .with(category("other"))
            .with(sided(SERVER, bool("catchServerErrors", config().getCatchServerErrors(), def.getCatchServerErrors(), config()::setCatchServerErrors)))
            .with(sided(CLIENT, bool("spawnEgg", config().getSpawnEgg(), def.getSpawnEgg(), config()::setSpawnEgg)))
            .with(sided(CLIENT, bool("playerHead", config().getPlayerHead(), def.getPlayerHead(), config()::setPlayerHead)));

        return options;
    }

}
