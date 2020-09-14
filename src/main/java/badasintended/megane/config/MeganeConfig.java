package badasintended.megane.config;

import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MeganeConfig {

    public final Inventory inventory = new Inventory();
    public final Energy energy = new Energy();
    public final Fluid fluid = new Fluid();
    public final Progress progress = new Progress();

    public static class Inventory {
        private boolean enabled = true;
        private int rowSize = 9;
        private Set<Identifier> blacklist = new HashSet<>();

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public void setRowSize(int rowSize) {
            this.rowSize = rowSize;
        }

        public void setBlacklist(Set<Identifier> blacklist) {
            this.blacklist = blacklist;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public int getRowSize() {
            return rowSize;
        }

        public Set<Identifier> getBlacklist() {
            return blacklist;
        }
    }

    public static class Energy {
        private boolean enabled = true;
        private boolean expandWhenSneak = true;
        private Map<String, Integer> colors = new HashMap<>();
        private Map<String, String> units = new HashMap<>();
        private Set<Identifier> blacklist = new HashSet<>();

        public Energy() {
            units.put("megane", "E");
            units.put("indrev", "LF");

            colors.put("megane", 0xFF710C00);
            colors.put("astromine", 0xFF356D95);
            colors.put("techreborn", 0xFF800800);
            colors.put("indrev", 0xFF3B4ADE);
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

        public Set<Identifier> getBlacklist() {
            return blacklist;
        }
    }

    public static class Fluid {
        private boolean enabled = true;
        private boolean expandWhenSneak = true;
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

        public boolean isEnabled() {
            return enabled;
        }

        public boolean isExpandWhenSneak() {
            return expandWhenSneak;
        }

        public int getBarColor() {
            return barColor;
        }

        public Set<Identifier> getBlacklist() {
            return blacklist;
        }
    }

    public static class Progress {
        private boolean enabled = true;
        private Set<Identifier> blacklist = new HashSet<>();

        public Progress() {
            blacklist.add(Registry.BLOCK.getId(Blocks.FURNACE));
            blacklist.add(Registry.BLOCK.getId(Blocks.BLAST_FURNACE));
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public void setBlacklist(Set<Identifier> blacklist) {
            this.blacklist = blacklist;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public Set<Identifier> getBlacklist() {
            return blacklist;
        }
    }

}
