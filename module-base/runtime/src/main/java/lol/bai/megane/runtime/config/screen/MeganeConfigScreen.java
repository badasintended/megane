package lol.bai.megane.runtime.config.screen;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import lol.bai.megane.runtime.config.MeganeConfig;
import lol.bai.megane.runtime.config.widget.Side;
import lol.bai.megane.runtime.config.widget.SidedEntry;
import lol.bai.megane.runtime.util.MeganeUtils;
import mcp.mobius.waila.gui.screen.ConfigScreen;
import mcp.mobius.waila.gui.widget.ButtonEntry;
import mcp.mobius.waila.gui.widget.CategoryEntry;
import mcp.mobius.waila.gui.widget.ConfigListWidget;
import mcp.mobius.waila.gui.widget.value.BooleanValue;
import mcp.mobius.waila.gui.widget.value.ConfigValue;
import mcp.mobius.waila.gui.widget.value.InputValue;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

import static lol.bai.megane.runtime.config.widget.Side.AND;
import static lol.bai.megane.runtime.config.widget.Side.CLIENT;
import static lol.bai.megane.runtime.config.widget.Side.PLUS;
import static lol.bai.megane.runtime.config.widget.Side.SERVER;

public class MeganeConfigScreen extends ConfigScreen {

    private static final Predicate<String> ALL = s -> true;
    private static final Predicate<String> HEX = s -> s.matches("^[a-fA-F\\d]*$");
    private static final Predicate<String> INT = s -> s.matches("^\\d*$");
    private static final Predicate<String> NAMESPACE = s -> s.matches("^[a-z\\d_.-]*$");
    private static final Predicate<String> IDENTIFIER = s -> s.matches("^[a-z\\d_./-]*$") || s.matches("^[a-z\\d_.-]*:[a-z\\d_./-]*$");

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

    private static Text tl(String key, Object... args) {
        return Text.translatable(key, args);
    }

    private static String tlKey(String type) {
        return "config.megane." + type;
    }

    private static CategoryEntry category(String type) {
        return new CategoryEntry(tlKey(type));
    }

    private static ButtonEntry button(String type, ButtonWidget.PressAction pressAction) {
        return new ButtonEntry(tlKey(type), 100, 20, pressAction);
    }

    private static BooleanValue bool(String type, boolean value, boolean defaultValue, Consumer<Boolean> consumer) {
        return new BooleanValue(tlKey(type), value, defaultValue, consumer);
    }

    private static <T> InputValue<T> input(String type, T t, T defaultValue, Consumer<T> consumer, Predicate<String> validator) {
        return new InputValue<>(tlKey(type), t, defaultValue, consumer, validator);
    }

    private static SidedEntry sided(Side side, ButtonEntry entry) {
        return new SidedEntry(side, entry);
    }

    private static SidedEntry sided(Side side, ConfigValue<?> value) {
        return new SidedEntry(side, value);
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public ConfigListWidget getOptions() {
        ConfigListWidget options = new ConfigListWidget(this, client, width, height, 32, height - 32, 26, MeganeUtils.CONFIG::save);
        options
            .with(category("inventory"))
            .with(sided(AND, bool("enabled", MeganeUtils.config().inventory.isEnabled(), def.inventory.isEnabled(), MeganeUtils.config().inventory::setEnabled)))
            .with(sided(SERVER, bool("inventory.itemCount", MeganeUtils.config().inventory.isItemCount(), def.inventory.isItemCount(), MeganeUtils.config().inventory::setItemCount)))
            .with(sided(SERVER, bool("inventory.nbt", MeganeUtils.config().inventory.isNbt(), def.inventory.isNbt(), MeganeUtils.config().inventory::setNbt)))
            .with(sided(CLIENT, input("inventory.maxWidth", MeganeUtils.config().inventory.getMaxWidth(), def.inventory.getMaxWidth(), MeganeUtils.config().inventory::setMaxWidth, INT)))
            .with(sided(CLIENT, input("inventory.maxHeight", MeganeUtils.config().inventory.getMaxHeight(), def.inventory.getMaxHeight(), MeganeUtils.config().inventory::setMaxHeight, INT)))
            .with(sided(PLUS, button("blacklist", w -> client.setScreen(new BlacklistConfigScreen(this, tl(tlKey("inventory.blacklist")), MeganeUtils.config().inventory.getBlacklist())))))

            .with(category("entityInventory"))
            .with(sided(AND, bool("enabled", MeganeUtils.config().entityInventory.isEnabled(), def.entityInventory.isEnabled(), MeganeUtils.config().entityInventory::setEnabled)))
            .with(sided(SERVER, bool("inventory.itemCount", MeganeUtils.config().entityInventory.isItemCount(), def.entityInventory.isItemCount(), MeganeUtils.config().entityInventory::setItemCount)))
            .with(sided(SERVER, bool("inventory.nbt", MeganeUtils.config().entityInventory.isNbt(), def.entityInventory.isNbt(), MeganeUtils.config().entityInventory::setNbt)))
            .with(sided(CLIENT, input("inventory.maxWidth", MeganeUtils.config().entityInventory.getMaxWidth(), def.entityInventory.getMaxWidth(), MeganeUtils.config().entityInventory::setMaxWidth, INT)))
            .with(sided(CLIENT, input("inventory.maxHeight", MeganeUtils.config().entityInventory.getMaxHeight(), def.entityInventory.getMaxHeight(), MeganeUtils.config().entityInventory::setMaxHeight, INT)))
            .with(sided(PLUS, button("blacklist", w -> client.setScreen(new BlacklistConfigScreen(this, tl(tlKey("inventory.blacklist")), MeganeUtils.config().entityInventory.getBlacklist())))))

            .with(category("energy"))
            .with(sided(AND, bool("enabled", MeganeUtils.config().energy.isEnabled(), def.energy.isEnabled(), MeganeUtils.config().energy::setEnabled)))
            .with(sided(CLIENT, bool("expand", MeganeUtils.config().energy.isExpandWhenSneak(), def.energy.isExpandWhenSneak(), MeganeUtils.config().energy::setExpandWhenSneak)))
            .with(sided(CLIENT, button("energy.unit", w -> client.setScreen(new MapConfigScreen<>(
                this, tl(tlKey("energy.unit")), MeganeUtils.config().energy.getUnits(), s -> s, s -> s, NAMESPACE, ALL, (prev, key, val) -> {
                MeganeUtils.config().energy.getUnits().remove(prev);
                if (key != null && val != null) {
                    MeganeUtils.config().energy.getUnits().put(key, val);
                }
            })))))
            .with(sided(CLIENT, button("energy.color", w -> client.setScreen(new MapConfigScreen<>(
                this, tl(tlKey("energy.color")), MeganeUtils.config().energy.getColors(), s -> s, INT2RGB, NAMESPACE, HEX, (prev, key, val) -> {
                MeganeUtils.config().energy.getColors().remove(prev);
                if (key != null && val != null) {
                    MeganeUtils.config().energy.getColors().put(key, Integer.parseUnsignedInt(val, 16) & 0xFFFFFF);
                }
            })))))
            .with(sided(PLUS, button("blacklist", w -> client.setScreen(new BlacklistConfigScreen(this, tl(tlKey("energy.blacklist")), MeganeUtils.config().energy.getBlacklist())))))

            .with(category("fluid"))
            .with(sided(AND, bool("enabled", MeganeUtils.config().fluid.isEnabled(), def.fluid.isEnabled(), MeganeUtils.config().fluid::setEnabled)))
            .with(sided(CLIENT, bool("expand", MeganeUtils.config().fluid.isExpandWhenSneak(), def.fluid.isExpandWhenSneak(), MeganeUtils.config().fluid::setExpandWhenSneak)))
            .with(sided(PLUS, button("blacklist", w -> client.setScreen(new BlacklistConfigScreen(this, tl(tlKey("fluid.blacklist")), MeganeUtils.config().fluid.getBlacklist())))))

            .with(category("progress"))
            .with(sided(AND, bool("enabled", MeganeUtils.config().progress.isEnabled(), def.progress.isEnabled(), MeganeUtils.config().progress::setEnabled)))
            .with(sided(CLIENT, bool("progress.showWhenZero", MeganeUtils.config().progress.isShowWhenZero(), def.progress.isShowWhenZero(), MeganeUtils.config().progress::setShowWhenZero)))
            .with(sided(PLUS, button("blacklist", w -> client.setScreen(new BlacklistConfigScreen(this, tl(tlKey("progress.blacklist")), MeganeUtils.config().progress.getBlacklist())))))

            .with(category("effect"))
            .with(sided(AND, bool("enabled", MeganeUtils.config().effect.isEnabled(), def.effect.isEnabled(), MeganeUtils.config().effect::setEnabled)))
            .with(sided(AND, bool("effect.level", MeganeUtils.config().effect.getLevel(), def.effect.getLevel(), MeganeUtils.config().effect::setLevel)))
            .with(sided(AND, bool("effect.hidden", MeganeUtils.config().effect.getHidden(), def.effect.getHidden(), MeganeUtils.config().effect::setHidden)))
            .with(sided(CLIENT, bool("effect.roman", MeganeUtils.config().effect.isRoman(), def.effect.isRoman(), MeganeUtils.config().effect::setRoman)))
            .with(sided(PLUS, button("blacklist", w -> client.setScreen(new BlacklistConfigScreen(this, tl(tlKey("effect.blacklist")), MeganeUtils.config().effect.getBlacklist())))))

            .with(category("other"))
            .with(sided(SERVER, bool("catchServerErrors", MeganeUtils.config().getCatchServerErrors(), def.getCatchServerErrors(), MeganeUtils.config()::setCatchServerErrors)))
            .with(sided(CLIENT, bool("spawnEgg", MeganeUtils.config().getSpawnEgg(), def.getSpawnEgg(), MeganeUtils.config()::setSpawnEgg)))
            .with(sided(CLIENT, bool("playerHead", MeganeUtils.config().getPlayerHead(), def.getPlayerHead(), MeganeUtils.config()::setPlayerHead)));

        return options;
    }

}
