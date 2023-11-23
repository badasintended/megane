package lol.bai.megane.runtime.config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.minecraft.resources.ResourceLocation;

import static lol.bai.megane.runtime.util.MeganeUtils.id;

public class MeganeConfig {

    private int configVersion = 0;

    public final Inventory inventory = new Inventory();
    public final Inventory entityInventory = new Inventory();
    public final Energy energy = new Energy();
    public final Fluid fluid = new Fluid();
    public final Progress progress = new Progress();
    public final Effect effect = new Effect();

    private boolean catchServerErrors = true;
    private boolean spawnEgg = true;
    private boolean playerHead = true;

    private boolean pluginConfigMigrated = false;
    private final Set<ResourceLocation> migratedBlacklist = new HashSet<>();

    public int getConfigVersion() {
        return configVersion;
    }

    public void setConfigVersion(int configVersion) {
        this.configVersion = configVersion;
    }

    public boolean getCatchServerErrors() {
        return catchServerErrors;
    }

    public boolean getSpawnEgg() {
        return spawnEgg;
    }

    public boolean getPlayerHead() {
        return playerHead;
    }

    public boolean isPluginConfigMigrated() {
        return pluginConfigMigrated;
    }

    public Set<ResourceLocation> getMigratedBlacklist() {
        return migratedBlacklist;
    }

    public void setCatchServerErrors(boolean catchServerErrors) {
        this.catchServerErrors = catchServerErrors;
    }

    public void setSpawnEgg(boolean spawnEgg) {
        this.spawnEgg = spawnEgg;
    }

    public void setPlayerHead(boolean playerHead) {
        this.playerHead = playerHead;
    }

    public void setPluginConfigMigrated(boolean pluginConfigMigrated) {
        this.pluginConfigMigrated = pluginConfigMigrated;
    }

    public interface Base {

        boolean isEnabled();

        Set<ResourceLocation> getBlacklist();

    }

    public static class Inventory implements Base {

        private boolean enabled = true;
        private boolean itemCount = true;
        private boolean nbt = false;
        private int maxWidth = 9;
        private int maxHeight = 3;
        private final Set<ResourceLocation> blacklist = new HashSet<>();

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public void setItemCount(boolean itemCount) {
            this.itemCount = itemCount;
        }

        public void setNbt(boolean nbt) {
            this.nbt = nbt;
        }

        public void setMaxWidth(int maxWidth) {
            this.maxWidth = maxWidth;
        }

        public void setMaxHeight(int maxHeight) {
            this.maxHeight = maxHeight;
        }

        @Override
        public boolean isEnabled() {
            return enabled;
        }

        public boolean isItemCount() {
            return itemCount;
        }

        public boolean isNbt() {
            return nbt;
        }

        public int getMaxWidth() {
            return maxWidth;
        }

        public int getMaxHeight() {
            return maxHeight;
        }

        @Override
        public Set<ResourceLocation> getBlacklist() {
            return blacklist;
        }

    }

    public static class Energy implements Base {

        private boolean enabled = true;
        private boolean expandWhenSneak = false;
        private final Map<String, Integer> colors = new HashMap<>();
        private final Map<String, String> units = new HashMap<>();
        private final Set<ResourceLocation> blacklist = new HashSet<>();

        public Energy() {
            units.put("megane", "E");

            colors.put("megane", 0x710C00);

            blacklist.add(id("appliedenergistics2", "energy_acceptor"));
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public void setExpandWhenSneak(boolean expandWhenSneak) {
            this.expandWhenSneak = expandWhenSneak;
        }

        @Override
        public boolean isEnabled() {
            return enabled;
        }

        public boolean isExpandWhenSneak() {
            return expandWhenSneak;
        }

        public Map<String, String> getUnits() {
            return units;
        }

        public Map<String, Integer> getColors() {
            return colors;
        }

        @Override
        public Set<ResourceLocation> getBlacklist() {
            return blacklist;
        }

    }

    public static class Fluid implements Base {

        private boolean enabled = true;
        private boolean expandWhenSneak = false;
        private final Set<ResourceLocation> blacklist = new HashSet<>();

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public void setExpandWhenSneak(boolean expandWhenSneak) {
            this.expandWhenSneak = expandWhenSneak;
        }

        @Override
        public boolean isEnabled() {
            return enabled;
        }

        public boolean isExpandWhenSneak() {
            return expandWhenSneak;
        }

        @Override
        public Set<ResourceLocation> getBlacklist() {
            return blacklist;
        }

    }

    public static class Progress implements Base {

        private boolean enabled = true;
        private boolean showWhenZero = false;
        private final Set<ResourceLocation> blacklist = new HashSet<>();

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public void setShowWhenZero(boolean showWhenZero) {
            this.showWhenZero = showWhenZero;
        }

        @Override
        public boolean isEnabled() {
            return enabled;
        }

        public boolean isShowWhenZero() {
            return showWhenZero;
        }

        @Override
        public Set<ResourceLocation> getBlacklist() {
            return blacklist;
        }

    }

    public static class Effect implements Base {

        private boolean enabled = true;
        private boolean level = true;
        private boolean roman = false;
        private boolean hidden = false;
        private final Set<ResourceLocation> blacklist = new HashSet<>();

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public void setLevel(boolean level) {
            this.level = level;
        }

        public void setRoman(boolean roman) {
            this.roman = roman;
        }

        public void setHidden(boolean hidden) {
            this.hidden = hidden;
        }

        @Override
        public boolean isEnabled() {
            return enabled;
        }

        public boolean getLevel() {
            return level;
        }

        public boolean isRoman() {
            return roman;
        }

        public boolean getHidden() {
            return hidden;
        }

        @Override
        public Set<ResourceLocation> getBlacklist() {
            return blacklist;
        }

    }

}
