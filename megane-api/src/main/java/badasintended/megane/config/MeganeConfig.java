package badasintended.megane.config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@SuppressWarnings("unused")
public class MeganeConfig {

    public int configVersion = 0;

    public final Inventory inventory = new Inventory();
    public final Inventory entityInventory = new Inventory();
    public final Energy energy = new Energy();
    public final Fluid fluid = new Fluid();
    public final Progress progress = new Progress();
    public final PetOwner petOwner = new PetOwner();
    public final Effect effect = new Effect();

    private boolean spawnEgg = true;
    private boolean playerHead = true;

    public boolean getSpawnEgg() {
        return spawnEgg;
    }

    public boolean getPlayerHead() {
        return playerHead;
    }

    public void setSpawnEgg(boolean spawnEgg) {
        this.spawnEgg = spawnEgg;
    }

    public void setPlayerHead(boolean playerHead) {
        this.playerHead = playerHead;
    }

    public interface Base {

        boolean isEnabled();

        Set<Identifier> getBlacklist();

    }

    public static class Inventory implements Base {

        private boolean enabled = true;
        private int maxWidth = 9;
        private int maxHeight = 3;
        private Set<Identifier> blacklist = new HashSet<>();

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public void setMaxWidth(int maxWidth) {
            this.maxWidth = maxWidth;
        }

        public void setMaxHeight(int maxHeight) {
            this.maxHeight = maxHeight;
        }

        public void setBlacklist(Set<Identifier> blacklist) {
            this.blacklist = blacklist;
        }

        @Override
        public boolean isEnabled() {
            return enabled;
        }

        public int getMaxWidth() {
            return maxWidth;
        }

        public int getMaxHeight() {
            return maxHeight;
        }

        @Override
        public Set<Identifier> getBlacklist() {
            return blacklist;
        }

    }

    public static class Energy implements Base {

        private boolean enabled = true;
        private boolean expandWhenSneak = false;
        private Map<String, Integer> colors = new HashMap<>();
        private Map<String, String> units = new HashMap<>();
        private Set<Identifier> blacklist = new HashSet<>();

        public Energy() {
            units.put("megane", "E");
            units.put("indrev", "LF");
            units.put("appliedenergistics2", "AE");
            units.put("modern_industrialization", "EU");

            colors.put("megane", 0x710C00);
            colors.put("astromine", 0x356D95);
            colors.put("techreborn", 0x800800);
            colors.put("indrev", 0x3B4ADE);
            colors.put("appliedenergistics2", 0x64099F);
            colors.put("modern_industrialization", 0xB70000);

            blacklist.add(new Identifier("appliedenergistics2", "energy_acceptor"));
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public void setExpandWhenSneak(boolean expandWhenSneak) {
            this.expandWhenSneak = expandWhenSneak;
        }

        public void setColors(Map<String, Integer> colors) {
            this.colors = colors;
        }

        public void setUnits(Map<String, String> units) {
            this.units = units;
        }

        public void setBlacklist(Set<Identifier> blacklist) {
            this.blacklist = blacklist;
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
        public Set<Identifier> getBlacklist() {
            return blacklist;
        }

    }

    public static class Fluid implements Base {

        private boolean enabled = true;
        private boolean expandWhenSneak = false;
        private int barColor = 0xFF0D0D59;
        private Set<Identifier> blacklist = new HashSet<>();

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public void setExpandWhenSneak(boolean expandWhenSneak) {
            this.expandWhenSneak = expandWhenSneak;
        }

        public void setBarColor(int barColor) {
            this.barColor = barColor;
        }

        public void setBlacklist(Set<Identifier> blacklist) {
            this.blacklist = blacklist;
        }

        @Override
        public boolean isEnabled() {
            return enabled;
        }

        public boolean isExpandWhenSneak() {
            return expandWhenSneak;
        }

        public int getBarColor() {
            return barColor;
        }

        @Override
        public Set<Identifier> getBlacklist() {
            return blacklist;
        }

    }

    public static class Progress implements Base {

        private boolean enabled = true;
        private boolean showWhenZero = false;
        private Set<Identifier> blacklist = new HashSet<>();

        public Progress() {
            blacklist.add(Registry.BLOCK.getId(Blocks.FURNACE));
            blacklist.add(Registry.BLOCK.getId(Blocks.BLAST_FURNACE));
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public void setShowWhenZero(boolean showWhenZero) {
            this.showWhenZero = showWhenZero;
        }

        public void setBlacklist(Set<Identifier> blacklist) {
            this.blacklist = blacklist;
        }

        @Override
        public boolean isEnabled() {
            return enabled;
        }

        public boolean isShowWhenZero() {
            return showWhenZero;
        }

        @Override
        public Set<Identifier> getBlacklist() {
            return blacklist;
        }

    }

    public static class PetOwner implements Base {

        private boolean enabled = true;
        private boolean offline = true;
        private Set<Identifier> blacklist = new HashSet<>();

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public void setOffline(boolean offline) {
            this.offline = offline;
        }

        public void setBlacklist(Set<Identifier> blacklist) {
            this.blacklist = blacklist;
        }

        @Override
        public boolean isEnabled() {
            return enabled;
        }

        public boolean isOffline() {
            return offline;
        }

        @Override
        public Set<Identifier> getBlacklist() {
            return blacklist;
        }

    }

    public static class Effect implements Base {

        private boolean enabled = true;
        private boolean level = true;
        private boolean roman = false;
        private boolean hidden = false;
        private Set<Identifier> blacklist = new HashSet<>();

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

        public void setBlacklist(Set<Identifier> blacklist) {
            this.blacklist = blacklist;
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
        public Set<Identifier> getBlacklist() {
            return blacklist;
        }

    }

}
